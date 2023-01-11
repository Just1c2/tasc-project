package vn.tass.microservice.model.constants;

public interface PAYMENT {

    class STATUS {
        public static final int ACTIVE = 1;
        public static final int NOT_ACTIVE = 2;
    }

    class ORDER_PAYMENT_STATS {
        public static final int SUCCESS = 1;
        public static final int FAIL = 0;
    }
}
