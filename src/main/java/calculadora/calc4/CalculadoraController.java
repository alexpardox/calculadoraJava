package calculadora.calc4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculadoraController {

    @GetMapping("/")
    public String calculadora(Model model) {
        model.addAttribute("resultado", "0");
        return "calculadora";
    }

    @PostMapping("/calcular")
    public String calcular(@RequestParam("numero1") String num1,
                          @RequestParam("operacion") String operacion,
                          @RequestParam("numero2") String num2,
                          Model model) {
        
        try {
            double numero1 = Double.parseDouble(num1);
            double numero2 = Double.parseDouble(num2);
            double resultado = 0;
            String operacionTexto = "";

            switch (operacion) {
                case "suma":
                    resultado = numero1 + numero2;
                    operacionTexto = numero1 + " + " + numero2 + " = " + resultado;
                    break;
                case "resta":
                    resultado = numero1 - numero2;
                    operacionTexto = numero1 + " - " + numero2 + " = " + resultado;
                    break;
                case "multiplicacion":
                    resultado = numero1 * numero2;
                    operacionTexto = numero1 + " × " + numero2 + " = " + resultado;
                    break;
                case "division":
                    if (numero2 != 0) {
                        resultado = numero1 / numero2;
                        operacionTexto = numero1 + " ÷ " + numero2 + " = " + resultado;
                    } else {
                        model.addAttribute("error", "Error: División por cero");
                        model.addAttribute("resultado", "0");
                        return "calculadora";
                    }
                    break;
                default:
                    model.addAttribute("error", "Operación no válida");
                    model.addAttribute("resultado", "0");
                    return "calculadora";
            }

            model.addAttribute("resultado", String.valueOf(resultado));
            model.addAttribute("operacion", operacionTexto);
            model.addAttribute("numero1", num1);
            model.addAttribute("numero2", num2);
            model.addAttribute("operacionSeleccionada", operacion);

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Error: Ingrese números válidos");
            model.addAttribute("resultado", "0");
        }

        return "calculadora";
    }
}
