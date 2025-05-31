package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class CountByMinimalPointRequest extends Request {

    private final Double minimalPoint;

    public CountByMinimalPointRequest(Double minimalPoint, User user) {
        super(CommandType.COUNT_BY_MINIMAL_POINT.name(), user);
        this.minimalPoint = minimalPoint;
    }

    public double getMinimalPoint() {
        return minimalPoint;
    }
}
