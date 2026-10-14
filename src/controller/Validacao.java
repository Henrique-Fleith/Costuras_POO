package controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validacao {

    public static double numero(String texto, String campo) {
        try {
            double v = Double.parseDouble(texto.trim().replace(",", "."));
            if (v < 0) throw new IllegalArgumentException(campo + " não pode ser negativo.");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(campo + " inválido. Use apenas números (ex: 2.5).");
        }
    }


    public static double numeroOpcional(String texto, String campo) {
        return texto == null || texto.isBlank() ? 0 : numero(texto, campo);
    }

    public static String data(String texto, String campo) {
        try {
            return LocalDate.parse(texto.trim()).toString();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(campo + " inválida. Use o formato AAAA-MM-DD (ex: 2026-10-06).");
        }
    }

    public static String dataOpcional(String texto, String campo) {
        return texto == null || texto.isBlank() ? null : data(texto, campo);
    }
}
