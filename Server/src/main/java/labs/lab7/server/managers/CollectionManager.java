package labs.lab7.server.managers;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.managers.CSVParser;
import labs.lab7.common.models.Difficulty;
import labs.lab7.common.models.Discipline;
import labs.lab7.common.models.LabWork;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Класс, управляющий коллекцией объектов класса {@link LabWork}.
 */
public class CollectionManager {
    private final Map<Long, LabWork> labWorks = new HashMap<>();
    private final Set<LabWork> collection = new LinkedHashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DatabaseManager databaseManager;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CollectionManager(CSVParser csvParser, DatabaseManager databaseManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.databaseManager = databaseManager;
    }

    /**
     * @return последнее время инициализации
     */
    public LocalDateTime getLastInitTime() {
        try {
            lock.readLock().lock();
            return lastInitTime;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @return последнее время сохранения
     */
    public LocalDateTime getLastSaveTime() {
        try {
            lock.readLock().lock();
            return lastSaveTime;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @return коллекция
     */
    public Set<LabWork> getCollection() {
        try {
            lock.readLock().lock();
            return collection;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Отображение из {@code id} в {@code labWork}.
     * @param id интересующий {@code id}
     * @return null, в случае отсутствия {@code id}, и {@code labWork} в ином случае.
     */
    public LabWork getLabWorkById(long id) {
        try {
            lock.readLock().lock();
            return labWorks.get(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Проверка, содержится ли {@code labWork} в коллекции.
     * @param lab интересующий {@code labWork}
     * @return результат проверки
     */
    public boolean contains(LabWork lab) {
        if (Objects.isNull(lab)) {
            return false;
        }
        try {
            lock.readLock().lock();
            return labWorks.containsKey(lab.getId());
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Проверка, содержится ли {@code labWork} с определённым {@code id} в коллекции.
     * @param id интересующий {@code id}
     * @return результат проверки
     */
    public boolean contains(Long id) {
        if (Objects.isNull(id)) {
            return false;
        }
        try {
            lock.readLock().lock();
            return labWorks.containsKey(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @return размер коллекции
     */
    public int size() {
        try {
            lock.readLock().lock();
            return collection.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Добавляет {@code labWork} в коллекцию.
     * @param lab {@code labWork}, который нужно добавить
     * @param userId id пользователя
     * @return id добавленного обёекта
     */
    public long add(LabWork lab, long userId) {
        if (Objects.isNull(lab) || contains(lab)) {
            return 0;
        }
        lock.writeLock().lock();
        var id = databaseManager.add(lab, userId);
        labWorks.put(id, lab);
        collection.add(lab);
        lock.writeLock().unlock();
        sortCollection();
        return id;
    }

    /**
     * Обновляет {@code LabWork} в коллекции
     * @param lab новое значение {@code LabWork}
     * @param userId id пользователя
     * @return успешность обновления
     */
    public boolean update(LabWork lab, long userId) {
        if (Objects.isNull(lab) || contains(lab)) {
            return false;
        }
        lock.writeLock().lock();
        try {
            databaseManager.update(lab, userId);
        } catch (AuthorizationException e) {
            lock.writeLock().unlock();
            return false;
        }
        labWorks.put(lab.getId(), lab);
        collection.removeIf(labWork -> labWork.getId().equals(lab.getId()));
        collection.add(lab);
        lock.writeLock().unlock();
        sortCollection();
        return true;
    }

    /**
     * Удаляет LabWork по id из коллекции
     * @param id удаляемое id
     * @param userId id пользователя
     * @return произошло ли удаление
     */
    public boolean remove(Long id, long userId) {
        if (Objects.isNull(id)) return false;

        LabWork removed = getLabWorkById(id);
        if (Objects.isNull(removed)) return false;

        try {
            lock.writeLock().lock();

            int deleted = databaseManager.remove(id, userId);
            if (deleted == 0) {
                System.out.println("Удаление запрещено: вы не автор этой работы.");
                return false;
            }

            labWorks.remove(id);
            collection.removeIf(lw -> lw.getId().equals(id)); // ← вот это ключ

            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }




    /**
     * Проверяет, является ли {@code labWork} максимальным среди всей коллекции.
     * @param targetLab проверяемый {@code labWork}
     * @return результат проверки
     */
    public boolean isMaxElement(LabWork targetLab) {
        if (Objects.isNull(targetLab)) {
            return false;
        }
        try {
            lock.readLock().lock();
            return collection.stream().noneMatch(lab -> targetLab.compareTo(lab) <= 0);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Удаляет из коллекции все {@code labWork}, меньшие, чем заданный.
     * @param targetLab заданный {@code labWork}
     * @param userId id пользователя
     */
    public void removeLower(LabWork targetLab, long userId) {
        if (Objects.isNull(targetLab)) {
            return;
        }
        lock.readLock().lock();
        List<Long> ids = collection.stream()
                .filter(lab -> lab.compareTo(targetLab) < 0)
                .map(LabWork::getId)
                .toList();
        lock.readLock().unlock();
        ids.forEach(id -> remove(id, userId));
    }

    /**
     * Считает количество {@code labWork} с заданным {@code minimalPoint} во всей коллекции.
     * @param minimalPoint искомый {@code minimalPoint}
     * @return количество таких элементов
     */
    public int countEqualMinimalPoint(Double minimalPoint) {
        lock.readLock().lock();
        int count = 0;
        for (LabWork lab : collection) {
            if (Objects.equals(lab.getMinimalPoint(), minimalPoint)) {
                count++;
            }
        }
        lock.readLock().unlock();
        return count;
    }

    /**
     * Собирает в {@code List} значения поля discipline всех элементов коллекции в порядке возрастания.
     * @return {@code List}, состоящий из {@code Discipline}
     */
    public List<Discipline> getFieldAscendingDiscipline() {
        try {
            lock.readLock().lock();
            return collection.stream().map(LabWork::getDiscipline).toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Собирает в {@code List} значения поля difficulty всех элементов коллекции в порядке убывания.
     * @return {@code List}, состоящий из {@code Difficulty}
     */
    public List<Difficulty> getFieldDescendingDifficulty() {
        lock.readLock().lock();
        List<Difficulty> result = collection.stream()
                .map(LabWork::getDifficulty)
                .collect(Collectors.toCollection(ArrayList::new));
        lock.readLock().unlock();
        Collections.reverse(result);
        return result;
    }

    /**
     * Очищает коллекцию.
     * @param userId id пользователя
     */
    public void clear(long userId) {
        lock.writeLock().lock();
        databaseManager.clear(userId);
        collection.clear();
        labWorks.clear();
        lock.writeLock().unlock();
    }

    /**
     * Сортирует элементы коллекции в порядке неубывания.
     */
    public void sortCollection() {
        lock.readLock().lock();
        List<LabWork> list = new ArrayList<>(collection);
        lock.readLock().unlock();

        Collections.sort(list);

        lock.writeLock().lock();
        collection.clear();
        collection.addAll(list);
        lock.writeLock().unlock();
    }

    /**
     * Загружает элементы в коллекцию из файла.
     * @return корректность уникальности {@code id} полученных элементов
     */
    public boolean loadCollection() {
        lock.writeLock().lock();
        labWorks.clear();
        collection.addAll(databaseManager.loadLabWorks());
        lastInitTime = LocalDateTime.now();
        for (LabWork lab : collection) {
            if (labWorks.containsKey(lab.getId())) {
                collection.clear();
                lock.writeLock().unlock();
                return false;
            } else {
                labWorks.put(lab.getId(), lab);
            }
        }
        lock.writeLock().unlock();
        sortCollection();
        return true;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        lock.readLock().lock();
        for (LabWork lab : collection) {
            info.append(lab.toString()).append(System.lineSeparator());
        }
        lock.readLock().unlock();
        return info.substring(0, Math.max(0, info.length() - System.lineSeparator().length()));
    }
}