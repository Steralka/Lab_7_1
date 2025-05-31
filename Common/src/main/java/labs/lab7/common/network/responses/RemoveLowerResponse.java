package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class RemoveLowerResponse extends Response {

    private final int removed;

    public RemoveLowerResponse(int removed, String errorMessage) {
        super(CommandType.REMOVE_LOWER.name(), errorMessage);
        this.removed = removed;
    }

    public int getRemoved() {
        return removed;
    }
}
