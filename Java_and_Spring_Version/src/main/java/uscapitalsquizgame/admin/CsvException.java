package uscapitalsquizgame.admin;

class CsvException extends RuntimeException {
    CsvException(String line, String messageEnd) {
        super(String.format("Line \"%s\" %s.", line, messageEnd));
    }
    
    CsvException(String message) {
        super(message);
    }
}
