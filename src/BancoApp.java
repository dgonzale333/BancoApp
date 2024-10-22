// Excepciones personalizadas
class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

class MontoInvalidoException extends Exception {
    public MontoInvalidoException(String mensaje) {
        super(mensaje);
    }
}

// Clase CuentaBancaria
class CuentaBancaria {
    private String titular;
    private double saldo;

    public CuentaBancaria(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    // Método para retirar dinero
    public void retirar(double monto) throws SaldoInsuficienteException, MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto a retirar debe ser mayor a cero.");
        }
        if (saldo < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para retirar " + monto);
        }
        saldo -= monto;
    }

    // Método para depositar dinero
    public void depositar(double monto) throws MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto a depositar debe ser mayor a cero.");
        }
        saldo += monto;
    }

    // Método para transferir dinero a otra cuenta
    public void transferir(CuentaBancaria cuentaDestino, double monto) throws SaldoInsuficienteException, MontoInvalidoException {
        if (cuentaDestino == null) {
            throw new IllegalArgumentException("La cuenta de destino no puede ser nula.");
        }
        this.retirar(monto);  // Retira dinero de esta cuenta
        cuentaDestino.depositar(monto);  // Deposita dinero en la cuenta destino
    }

    @Override
    public String toString() {
        return "Titular: " + titular + ", Saldo: " + saldo;
    }
}

// Clase principal BancoApp
public class BancoApp {

    public static void main(String[] args) {
        // Creación de dos cuentas bancarias
        CuentaBancaria cuenta1 = new CuentaBancaria("Juan Pérez", 1000);
        CuentaBancaria cuenta2 = new CuentaBancaria("María García", 500);

        try {
            // Operaciones en la cuenta de Juan
            System.out.println("Cuenta de Juan antes de operaciones: " + cuenta1);
            cuenta1.retirar(200);  // Retira 200 de la cuenta de Juan
            cuenta1.depositar(300);  // Deposita 300 en la cuenta de Juan
            System.out.println("Cuenta de Juan después de retirar y depositar: " + cuenta1);

            // Transferencia de Juan a María
            cuenta1.transferir(cuenta2, 400);  // Transfiere 400 de Juan a María
            System.out.println("Cuentas después de la transferencia:");
            System.out.println("Cuenta de Juan: " + cuenta1);
            System.out.println("Cuenta de María: " + cuenta2);

        } catch (SaldoInsuficienteException | MontoInvalidoException e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }

        try {
            // Intentar retirar más de lo que tiene María
            cuenta2.retirar(600);  // Esto debería lanzar una excepción
        } catch (SaldoInsuficienteException | MontoInvalidoException e) {
            System.out.println("Error al retirar: " + e.getMessage());
        }

        try {
            // Intentar depositar un monto negativo en la cuenta de Juan
            cuenta1.depositar(-100);  // Esto debería lanzar una excepción
        } catch (MontoInvalidoException e) {
            System.out.println("Error al depositar: " + e.getMessage());
        }
    }
}
