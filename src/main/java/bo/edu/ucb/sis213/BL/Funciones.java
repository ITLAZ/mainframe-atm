package bo.edu.ucb.sis213.BL;

import bo.edu.ucb.sis213.DAO.Query;

public class Funciones {
    static int id, pinBDD;
    static String aliasBDD, nombre;
    static double saldo;



    public boolean login(String alias, int pin){
        Query query = new Query();
        aliasBDD = query.getAlias(pin);
        pinBDD = query.getPin(alias);
            if (alias.equals(aliasBDD) && pin == pinBDD) {
                id = query.getID(aliasBDD, pinBDD);
                nombre = query.getNombre(aliasBDD, pinBDD);
                saldo = query.getSaldo(aliasBDD, pinBDD);
                return true;
            } else {
                return false;
            }
    }

    public String consultaSaldo() {
        return "SU SALDO ACTUAL ES: " + saldo;
    }

    public String setName(){
        return nombre;
    }

    public boolean retiro(double cantidad) {
        Query query = new Query();     
        cantidad = cantidad * -1;
        if(query.setSaldo(id, cantidad)){
            query.insertHistorico(id, "RETIRO", cantidad * -1);
            saldo = saldo + cantidad;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deposito(double cantidad) {
        Query query = new Query();
        if(query.setSaldo(id, cantidad)){
            query.insertHistorico(id, "DEPOSITO", cantidad);
            saldo = saldo + cantidad;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean cambioPIN(int oldPIN, int newPIN){
        Query query = new Query();
        if(pinBDD == oldPIN){
            if(query.setPin(id, newPIN)){
                return true;
            }
        }
        return false;
    }

}
