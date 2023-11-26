package RdF.Server;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Classe di utility per array/liste, controllo di tipo, altro
 *
 * @author aepiotti
 */
public class Utils {

    public static DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

    /**
     * Costruttore
     */
    public Utils() {
        super();
    }

    /**
     * Mette in output variabili di diversa natura Se la variabile e' un array,
     * viene chiamato ricorsivamente il metodo convertendo, eventualmente,
     * l'array di primitivi ad un array di oggetti
     *
     * @param sep separatore tra gli oggetti
     * @param end carattere finale
     * @param var variabili da mettere in output
     */
    public static void output(String sep, String end, Object... var) {
        if (var == null) {
            return;
        }
        for (Object el : var) {
            if (el != null && el.getClass().isArray()) {
                output(sep, end, getArray(el));
            } else {
                System.out.print(el + sep);
            }
        }
        System.out.print(end);
    }

    /* N.B.
     * niente metodo che permette di stabilire il separatore altrimenti non si
     * possono piu' mandare in output le stringhe
     * sbaglia la scelta del metodo
     */
    /**
     * Richiama il metodo output passando come separatore e stringa di fine
     * linea uno spazio
     *
     * @param var le variabili da mandare in output
     */
    public static void print(Object... var) {
        output(" ", " ", var);
    }

    /**
     * Richiama il metodo output passando come separatore uno spazio e come
     * stringa di fine linea un \n
     *
     * @param var le variabili da mandare in output
     */
    public static void println(Object... var) {
        output(" ", "\n", var);
    }
    /**
     * Manda in ouput sullo stream di errore
     *
     * @param var variabili da mandare in ouput
     */
    public static void error(Object... var) {
        System.err.println(join(" ", var));
    }

    /**
     * Converte un array di primitivi ad un array di oggetti
     *
     * @param val array, di primitivi o di oggetti
     * @return array di oggetti
     */
    public static Object[] getArray(Object val) {
        int length = Array.getLength(val);
        Object[] array = new Object[length];
        for (int i = 0; i < length; ++i) {
            array[i] = Array.get(val, i);
        }
        return array;
    }

    /**
     * Controlla se un oggetto e' null e nel caso ritorna il valore di default.
     *
     * @param <T> il tipo del valore di default
     * @param obj l'oggetto da controllare
     * @param defaultValue il valore di default
     * @return l'oggetto se diverso da null altrimenti il valore di default
     */
    @SuppressWarnings("unchecked")
    public static <T> T avoidNull(Object obj, T defaultValue) {
        if (obj != null) {
            return (T) obj;
        } else {
            return defaultValue;
        }
    }
    /**
     * Dato un array, prende l'elemento indicato dall'indice. Se questo non e'
     * presente, ritorna il valore di default
     *
     * @param <T> il tipo del valore di default
     * @param array l'array da cui estrarre l'elemento
     * @param index l'elemento che si vuole ottenere
     * @param defaultValue il valore di default da ritornare
     * @return l'elemento indicato dall'indice oppure il valore di default
     */
    @SuppressWarnings("unchecked")
    public static <T> T getOrElse(Object[] array, int index, T defaultValue) {
        if (index < array.length && index >= 0 && array[index] != null) {
            return (T) array[index];
        } else {
            return defaultValue;
        }
    }

    /**
     * Data una lista, prende l'elemento indicato dall'indice. Se questo non e'
     * presente, ritorna il valore di default
     *
     * @param <T> il tipo del valore di default
     * @param list la lista da cui estrarre l'elemento
     * @param index l'elemento che si vuole ottenere
     * @param defaultValue il valore di default da ritornare
     * @return l'elemento indicato dall'indice oppure il valore di default
     */
    @SuppressWarnings("unchecked")
    public static <T> T getOrElse(List list, int index, T defaultValue) {
        if (index < list.size() && index >= 0 && list.get(index) != null) {
            return (T) list.get(index);
        } else {
            return defaultValue;
        }
    }

    /**
     * Data una mappa, prende l'elemento indicato dalla chiave. Se questo non e'
     * presente, ritorna il valore di default
     *
     * @param <K> il tipo della chiave
     * @param <V> il tipo del valore
     * @param map la mappa da cui estrarre l'elemento
     * @param key l'elemento che si vuole ottenere
     * @param defaultValue il valore di default da ritornare
     * @return l'elemento indicato dall'indice oppure il valore di default
     */
    @SuppressWarnings("unchecked")
    public static <K, V> V getOrElse(Map<K, Object> map, K key, V defaultValue) {
        if (map.getOrDefault(key, defaultValue) != null) {
            return (V) map.getOrDefault(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * Taglia l'array e prende solo la parte specificata dagli indici
     *
     * @param array array da tagliare
     * @param startIndex indice iniziale
     * @param endIndex indice finale
     * @return la porzione dell'array
     */
    // N.B. Valore di ritorno Object per permettere il cast
    public static Object slice(Object[] array, int startIndex, int endIndex) {
        return Arrays.copyOfRange(array, startIndex, endIndex);
    }

    // per gli array di primitivi
    /**
     * Se il primo parametro  un array, lo taglia e prende solo la parte
     * specificata dagli indici
     *
     * @param array array da tagliare
     * @param startIndex indice iniziale
     * @param endIndex indice finale
     * @return la prozione di array
     */
    public static Object slice(Object array, int startIndex, int endIndex) {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException(
                    "Il primo parametro deve essere un array!");
        }
        return slice(getArray(array), startIndex, endIndex);
    }

    /**
     * Taglia la stringa e ritorna solo la parte indicata dagli indici
     *
     * @param str la stringa da tagliare
     * @param startIndex indice iniziale
     * @param endIndex indice finale
     * @return
     */
    public static String slice(String str, int startIndex, int endIndex) {
        return str.substring(startIndex, endIndex);
    }

    /**
     * Unisce un numero arbitrario di array in uno
     *
     * @param <T> i tipi degli array
     * @param first il primo array
     * @param rest tutti gli altri array
     * @return un array ottenuto unendoli tutti
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] merge(T[] first, T[]
            ... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Crea una stringa partendo da un array e suddividendo i vari valori con un
     * separatore
     *
     * @param sep separatore dei valori
     * @param array array da convertire
     * @return la stringa con i valori suddividi dal separatore
     */
    public static String join(String sep, Object... array) {
        String str = "";
        for (Object el : array) {
            str += String.valueOf(el) + sep;
        }
        return str.length() > 0 ? slice(str, 0, str.length() - sep.length())
                : str;
    }

    // per array di primitivi
    /**
     * Inizializza un array con uno specifico valore
     *
     * @param dimensions dimensioni dell'array
     * @param value valore da assegnare ad ogni cella
     * @return un array, matrice o array multidimensionale inizializzato
     */
    public static Object initArray(Object value, Object... dimensions) {
        if (dimensions.length == 0) {
            throw new IllegalArgumentException("Specifica la dimensione!");
        }
        Object[] array = (Object[]) Array.newInstance(value.getClass(),
                (int) dimensions[0]);
        for (int i = 0; i < array.length; i++) {
            if (dimensions.length > 1) {
                array[i] = initArray(value,
                        slice(dimensions, 1, dimensions.length));
            } else {

            }
        }
        return array;
    }

    /**
     * Crea un range
     *
     * @param start punto da cui iniziare a contare
     * @param end fino a che numero arrivare
     * @param step quanti numeri saltare da un indice all'altro
     *
     * @return un range
     */
    public static Integer[] range(int start, int end, int step) {
        Integer[] array = new Integer[(end - start) / step];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + start;
        }
        return array;
    }

    /**
     * Richiama il metodo merge settando lo step a 1
     *
     * @param start punto d'inizio
     * @param end punto finale
     * @return un range
     */
    public static Integer[] range(int start, int end) {
        return range(start, end, 1);
    }

    /**
     * Richiama range settando start e step a 1
     *
     * @param end punto finale
     * @return un range
     */
    public static Integer[] range(int end) {
        return range(1, end, 1);
    }

    /**
     * Trova, se presente, l'indice di un oggetto in un array
     *
     * @param haystack array da controllare
     * @param needle oggetto da cercare
     * @return indice dell'oggetto o -1 se non presente
     */
    public static int indexOf(Object haystack, Object needle) {
        if (haystack instanceof List) {
            return indexOf(haystack, needle);
        } else {
            return indexOf(Arrays.asList(getArray(haystack)), needle);
        }
    }

    /**
     * Trova, se presente, l'indice di un oggetto in una lista
     *
     * @param haystack lista da controllare
     * @param needle oggetto da cercare
     * @return indice dell'oggetto o -1 se non presente
     */
    public static int indexOf(List haystack, Object needle) {
        return haystack.indexOf(needle);
    }

    /**
     * Controlla l'appartenenza di un numero in un range
     *
     * @param start inizio del range
     * @param end fine del range
     * @param target numero da controllare
     * @return vero se appartiene, falso altrimenti
     */
    public static boolean in(double start, double end, double target) {
        return target >= start && target <= end;
    }

    /**
     * Controlla l'appartenenza di un oggetto a un array o lista
     *
     * @param haystack array o lista da controllare
     * @param needle oggetto da cercare
     * @return vero se e' presente, falso altrimenti
     */
    public static boolean in(Object haystack, Object needle) {
        return indexOf(haystack, needle) >= 0;
    }

    /**
     * Controlla l'appartenenza di una stringa in un'altra stringa
     *
     * @param str stringa da controllare
     * @param target stringa da cercare
     * @return vero se e' presente, falso altrimenti
     */
    public static boolean in(String str, String target) {
        return str.contains(target);
    }

    /**
     * Verifica se una stringa contiene un numerico
     *
     * @param str stringa da controllare
     * @return vero se e' un numerico, falso altrimenti
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Verifica se una stringa contiene un intero
     *
     * @param str stringa da controllare
     * @return vero se e' un intero, falso altrimenti
     */
    public static boolean isInteger(String str) {
        return str.matches("-?\\d+");
    }

    /**
     * Verifica se una stringa contiene un intero strettamente positivo
     *
     * @param str stringa da controllare
     * @return vero se e' un intero positivo, falso altrimenti
     */
    public static boolean isPositiveInteger(String str) {
        return isInteger(str) && Integer.parseInt(str) > 0;
    }

    /**
     * Verifica se una stringa contiene un numero reale
     *
     * @param str stringa da controllare
     * @return vero se e' un numero reale, falso altrimenti
     */
    public static boolean isDouble(String str) {
        return str.matches("-?\\d+\\.\\d+");
    }

    /**
     * Verifica se una stringa contiene un booleano
     *
     * @param str stringa da controllare
     * @return vero se e' un booleano, falso altrimenti
     */
    public static boolean isBoolean(String str) {
        return str.matches("(true|false)");
    }

    /**
     * Verifica se una stringa contiene un carattere
     *
     * @param str stringa da controllare
     * @return vero se e' un carattere, falso altrimenti
     */
    public static boolean isChar(String str) {
        return str.matches("\\S");
    }

    /**
     * Tronca un reale a eccesso con n cifre decimali
     *
     * @param number il numero da troncare
     * @param decimalPlace il numero di decimali da considerare
     * @return il numero troncato
     */
    public static double round(double number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Verifica il tipo del valore della stringa e crea un oggetto di quel tipo
     *
     * @param value il valore da verificare
     * @return il valore castato se e' di un tipo riconoscibile altrimenti la
     * stessa stringa
     */
    public static Object valueOf(String value) {
        if (isInteger(value)) {
            return Integer.valueOf(value);
        } else if (isDouble(value)) {
            return Double.valueOf(value);
        } else if (isBoolean(value)) {
            return Boolean.valueOf(value);
        } else if (isChar(value)) {
            return value.charAt(0);
        } else {
            return value;
        }
    }

    /**
     * Casta al tipo del costruttore usato dall'oggetto
     *
     * @param <T> il tipo dell'oggetto
     * @param obj l'oggetto da castare
     * @return l'oggetto castato
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * Calcola la percentuale dei numeri passati come argomento.
     *
     */
    public static int percentage(int occurences, int total) {
        float occ = occurences;
        float tot = total;
        if (occurences == 0 || total == 0 ) {
            return 0;
        }
        int p = (int) (( occ * 100 ) / tot);
        return p;
    }


    public static String percentageToString(int occurences, int total) {
        String percSign = "%";
        return percentage(occurences, total) + percSign;
    }
}
