public class CreatureException {
    public static class BadParametersException extends Exception {
        public BadParametersException(String message) {
            super(message);
        }
    }

    public static class DeadHitException extends Exception {
        public DeadHitException(String message) {
            super(message);
        }
    }
}
