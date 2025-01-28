import java.io.*;
import java.util.*;
public class SistemaImpuestos {
    public static final double[][] TABLA_IMPUESTOS = {
        {11212, 0.0}, {14285, 0.05}, {17854, 0.10}, {21442, 0.12}, {42874, 0.15},
        {64297, 0.20}, {85729, 0.25}, {114288, 0.30}, {Double.MAX_VALUE, 0.35}
    };
    public static final double[] MONTOS_MAXIMOS_DEDUCCION = {10000, 3700, 3700, 3700, 15000, 1000};
    public static final String[] CATEGORIAS = {"Vivienda", "Educación", "Alimentación", "Vestimenta", "Salud", "Turismo"};

    public static double[] registrarSueldos(Scanner scanner) {
        double[] sueldosMensuales = new double[12];
        for (int i = 0; i < 12; i++) {
            System.out.print("Ingrese el sueldo para el mes " + (i + 1) + ": ");
            sueldosMensuales[i] = scanner.nextDouble();
        }
        return sueldosMensuales;
    }
    public static double[] registrarFacturas(Scanner scanner) {
        double[] facturasPorCategoria = new double[CATEGORIAS.length];
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.print("Ingrese el valor total de las facturas para " + CATEGORIAS[i] + " (máximo permitido: " + MONTOS_MAXIMOS_DEDUCCION[i] + "): ");
            double factura = scanner.nextDouble();
            if (factura > MONTOS_MAXIMOS_DEDUCCION[i]) {
                facturasPorCategoria[i] = MONTOS_MAXIMOS_DEDUCCION[i];
            } else {
                facturasPorCategoria[i] = factura;
            }
        }
        return facturasPorCategoria;
    }
    public static double calcularIngresosAnuales(double[] sueldosMensuales) {
        double ingresosAnuales = 0;
        for (int i = 0; i < sueldosMensuales.length; i++) {
            ingresosAnuales += sueldosMensuales[i];
        }
        return ingresosAnuales;
    }
    public static double calcularDeduccionesTotales(double[] facturasPorCategoria) {
        double deduccionesTotales = 0;
        for (int i = 0; i < facturasPorCategoria.length; i++) {
            deduccionesTotales += facturasPorCategoria[i];
        }
        return deduccionesTotales;
    }

    public static double calcularBaseImponible(double ingresosAnuales, double deduccionesTotales) {
        double baseImponible = ingresosAnuales - deduccionesTotales;
        if (baseImponible < 0) {
            baseImponible = 0;
        }
        return baseImponible;
    }
    public static double calcularImpuesto(double baseImponible) {
        double impuesto = 0;
        double restante = baseImponible;
        for (int i = 0; i < TABLA_IMPUESTOS.length; i++) {
            double limite = TABLA_IMPUESTOS[i][0];
            double porcentaje = TABLA_IMPUESTOS[i][1];

            if (restante <= limite) {
                impuesto += restante * porcentaje;
                break;
            } else {
                impuesto += limite * porcentaje;
                restante -= limite;
            }
        }
        return impuesto;
    }
    public static void mostrarFacturasDetalle(double[] facturasPorCategoria) {
        System.out.println("\n=== Detalle de Facturas ===");
        double totalFacturas = 0;
        for (int i = 0; i < CATEGORIAS.length; i++) {
            double porcentajeUtilizado = (facturasPorCategoria[i] / MONTOS_MAXIMOS_DEDUCCION[i]) * 100;
            double diferencia = MONTOS_MAXIMOS_DEDUCCION[i] - facturasPorCategoria[i];
            totalFacturas += facturasPorCategoria[i];
            System.out.printf("%s: $%.2f (%.2f%% utilizado, $%.2f restantes)\n",
                              CATEGORIAS[i], facturasPorCategoria[i], porcentajeUtilizado, diferencia);
        }
        System.out.printf("Total General de Facturas: $%.2f\n", totalFacturas);
    }
    public static void guardarDeclaracionEnArchivo(double ingresosAnuales, double deduccionesTotales, double baseImponible, double impuesto, double[] facturasPorCategoria) throws IOException {
        File archivo = new File("declaracion_impuestos.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
        writer.write("=== Declaración de Impuestos ===\n");
        writer.write("Ingresos Anuales: $" + ingresosAnuales + "\n");
        writer.write("Deducciones Totales: $" + deduccionesTotales + "\n");
        writer.write("Base Imponible: $" + baseImponible + "\n");
        writer.write("Impuesto a Pagar: $" + impuesto + "\n");
        writer.write("\n=== Detalle de Facturas ===\n");
        double totalFacturas = 0;
        for (int i = 0; i < CATEGORIAS.length; i++) {
            double porcentajeUtilizado = (facturasPorCategoria[i] / MONTOS_MAXIMOS_DEDUCCION[i]) * 100;
            double diferencia = MONTOS_MAXIMOS_DEDUCCION[i] - facturasPorCategoria[i];
            totalFacturas += facturasPorCategoria[i];
            writer.write(String.format("%s: $%.2f (%.2f%% utilizado, $%.2f restantes)\n",
                    CATEGORIAS[i], facturasPorCategoria[i], porcentajeUtilizado, diferencia));
        }
        writer.write(String.format("Total General de Facturas: $%.2f\n", totalFacturas));

        writer.close();
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        double[] sueldosMensuales = null;
        double[] facturasPorCategoria = null;
        double ingresosAnuales = 0;
        double deduccionesTotales = 0;
        double baseImponible = 0;
        double impuesto = 0;
        while (true) {
            System.out.println("\n=== Sistema de Declaración de Impuestos ===");
            System.out.println("1. Registrar Sueldos Mensuales");
            System.out.println("2. Registrar Facturas por Categoría");
            System.out.println("3. Calcular Impuestos");
            System.out.println("4. Mostrar Detalle de Facturas");
            System.out.println("5. Generar Declaración de Impuestos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    sueldosMensuales = registrarSueldos(scanner);
                    break;
                case 2:
                    facturasPorCategoria = registrarFacturas(scanner);
                    break;
                case 3:
                    if (sueldosMensuales == null || facturasPorCategoria == null) {
                        System.out.println("Primero debe registrar sueldos y facturas");
                    } else {
                        ingresosAnuales = calcularIngresosAnuales(sueldosMensuales);
                        deduccionesTotales = calcularDeduccionesTotales(facturasPorCategoria);
                        baseImponible = calcularBaseImponible(ingresosAnuales, deduccionesTotales);
                        impuesto = calcularImpuesto(baseImponible);
                        System.out.println("\nCálculo realizado correctamente");
                    }
                    break;
                case 4:
                    if (facturasPorCategoria == null) {
                        System.out.println("Primero debe registrar las facturas");
                    } else {
                        mostrarFacturasDetalle(facturasPorCategoria);
                    }
                    break;
                case 5:
                    if (ingresosAnuales == 0 || deduccionesTotales == 0) {
                        System.out.println("Primero debe calcular los impuestos");
                    } else {
                        guardarDeclaracionEnArchivo(ingresosAnuales, deduccionesTotales, baseImponible, impuesto, facturasPorCategoria);
                        System.out.println("Declaración guardada en 'declaracion_impuestos.txt'.");
                    }
                    break;
                case 6:
                    System.out.println("Saliendo del sistema");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida, intente nuevamente");
            }
        }
    }
}