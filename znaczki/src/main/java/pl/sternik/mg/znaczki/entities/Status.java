package pl.sternik.mg.znaczki.entities;


public enum Status {
    
    NOWY("Nowy"), 
    DO_SPRZEDANIA("Do sprzedania"),
    DUBLET("Dublet");
    
    
    public static final Status[] ALL = { NOWY, DO_SPRZEDANIA, DUBLET };
    
    
    private final String name;

    private Status(final String name) {
    	this.name = name;
    }
    
//    public static Status forName(final String name) {
//        if (name == null) {
//            throw new IllegalArgumentException("Nie mozna nula dla Status");
//        }
//        if (name.equalsIgnoreCase("NOWY")) {
//            return NOWA;
//        } else if (name.equalsIgnoreCase("DO_SPRZEDANIA")) {
//            return Status.DO_SPRZEDANIA;
//        }
//        throw new IllegalArgumentException("Nazwa \"" + name + "\" nie pasuje do zadengo Statusu");
//    }
//    
    
    public String getName() {
        return this.name;
    }
}
