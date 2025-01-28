# Programa para calcular impuestos a la renta y generar una declaración detallada

# Variables iniciales
sueldos_mensuales = []  # Lista para almacenar los sueldos mensuales
facturas = {            # Diccionario para almacenar las facturas por categoría
    "Vivienda": 0.0,
    "Educación": 0.0,
    "Alimentación": 0.0,
    "Vestimenta": 0.0,
    "Salud": 0.0,
    "Turismo": 0.0
}
tabla_impuestos = [     # Tabla de impuestos a la renta del 2023 (valores de ejemplo)
    (0, 11212, 0, 0),         # (Desde, Hasta, Impuesto base, Exceso porcentaje)
    (11212, 14285, 0, 5),
    (14285, 17854, 154, 10),
    (17854, 21442, 511, 12),
    (21442, 42874, 941, 15),
    (42874, 64297, 4156, 20),
    (64297, 85729, 8440, 25),
    (85729, 114288, 13798, 30),
    (114288, float("inf"), 22366, 35)
]

# Función para registrar los sueldos mensuales y calcular el ingreso anual
def obtener_ingreso_anual():
    """Solicita los sueldos mensuales al usuario y calcula el ingreso anual."""
    print("Ingrese sus sueldos mensuales:")
    for mes in range(1, 13):
        while True:
            try:
                sueldo = float(input(f"Mes {mes}: "))
                if sueldo < 0:
                    print("El sueldo no puede ser negativo. Inténtelo nuevamente.")
                else:
                    sueldos_mensuales.append(sueldo)
                    break
            except ValueError:
                print("Entrada inválida. Por favor, ingrese un número válido.")
    return sum(sueldos_mensuales)

# Función para registrar las facturas por categoría
def registrar_facturas():
    """Solicita al usuario que registre sus facturas por categoría."""
    print("\nIngrese los montos de facturas generadas por categoría:")
    for categoria in facturas.keys():
        while True:
            try:
                monto = float(input(f"{categoria}: "))
                if monto < 0:
                    print("El monto no puede ser negativo. Inténtelo nuevamente.")
                else:
                    facturas[categoria] = monto
                    break
            except ValueError:
                print("Entrada inválida. Por favor, ingrese un número válido.")

# Función para calcular el impuesto anual basado en la tabla de impuestos
def calcular_impuesto(ingreso_anual, deducciones):
    """Calcula el impuesto anual basado en el ingreso y las deducciones."""
    ingreso_gravable = max(0, ingreso_anual - deducciones)
    impuesto = 0
    for tramo in tabla_impuestos:
        desde, hasta, impuesto_base, exceso_porcentaje = tramo
        if desde <= ingreso_gravable <= hasta:
            impuesto = impuesto_base + (ingreso_gravable - desde) * (exceso_porcentaje / 100)
            break
    return impuesto

# Función para generar una declaración detallada de impuestos
def generar_declaracion(ingreso_anual, deducciones, impuesto):
    """Genera y muestra una declaración de impuestos detallada."""
    print("\nDECLARACIÓN DE IMPUESTOS")
    print("-----------------------")
    print(f"Ingreso anual: ${ingreso_anual:.2f}")
    print(f"Deducciones totales: ${deducciones:.2f}")
    print(f"Impuesto a pagar: ${impuesto:.2f}")

# Función principal que coordina todas las operaciones
def main():
    ingreso_anual = obtener_ingreso_anual()
    registrar_facturas()
    deducciones = sum(facturas.values())
    impuesto = calcular_impuesto(ingreso_anual, deducciones)
    generar_declaracion(ingreso_anual, deducciones, impuesto)

# Inicia la ejecución del programa si es el archivo principal
if __name__ == "__main__":
    main()

#Ingrese sus sueldos mensuales:
#Mes 1: 20000 
#Mes 2: 20000
#Mes 3: 20000
#Mes 4: 20000
#Mes 5: 20000
#Mes 6: 20000
#Mes 7: 40000 
#Mes 8: 50000
#Mes 9: 60000
#Mes 10: 70000
#Mes 11: 3000
#Mes 12: 20000

#Ingrese los montos de facturas generadas por categoría:
#Vivienda: 10000
#Educación: 2000
#Alimentación: 1000
#Vestimenta: 4000
#Salud: 5000
#Turismo: 20000 

#DECLARACIÓN DE IMPUESTOS
#-----------------------
#Ingreso anual: $363000.00
#Deducciones totales: $42000.00
#Impuesto a pagar: $94715.20   