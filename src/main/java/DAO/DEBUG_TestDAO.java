package DAO;

public class DEBUG_TestDAO {
    public static void main(String[] args) {
        AccesSQLDatabase db = new AccesSQLDatabase();
        db.getConnection();
        db.dropTable("Avions");
    }
}
