package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class CountByMinimalPointResponse extends Response {

    private final int count;

    public CountByMinimalPointResponse(int count, String errorMessage) {
        super(CommandType.COUNT_BY_MINIMAL_POINT.name(), errorMessage);
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
