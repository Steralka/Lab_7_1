package labs.lab7.common.utility;

import java.io.Serializable;

public record CommandInfo(String name, String description) implements Serializable {}
