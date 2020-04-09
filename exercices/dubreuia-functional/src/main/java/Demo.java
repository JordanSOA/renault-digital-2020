import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Retourne la liste des éléments commençant par "t", transformés en lowercase.
 *
 * Entrée: [Merope, Terpsichore, Hebe, Pheme, Themis, Eileithyia, Atropos]
 * Sortie: [terpsichore, themis]
 */
public class Demo {

    public static List<String> imperative(List<String> elements) {
        List<String> list = new ArrayList<>();
        for (String element : elements) {
            if (element.startsWith("T")){
               list.add(element.toLowerCase());
            }
        }
        return list;
    }

    public static List<String> functional(List<String> elements) {
        return elements.stream()
            .filter(element -> element.startsWith("T"))
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> names = List.of("Merope", "Terpsichore", "Hebe", "Pheme", "Themis", "Eileithyia", "Atropos");
        System.out.println("------------ Imperative ------------");
        System.out.println(imperative(names));
        System.out.println("------------ Functional ------------");
        System.out.println(functional(names));
    }

}