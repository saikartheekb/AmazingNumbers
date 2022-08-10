package numbers;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Welcome to Amazing Numbers!

                """);
        printRequests();
        long n = -1;
        int k;
        while (n != 0) {
            System.out.println("\nEnter a request: ");
            try {
                String[] temp = sc.nextLine().split(" ");
                if (temp.length > 2) {
                    n = Long.parseLong(temp[0]);
                    k = Integer.parseInt(temp[1]);
                    HashMap<String, Boolean> propMap = new HashMap<>();
                    for (int i = 2; i < temp.length; i++) {
                        propMap.put(temp[i], false);
                    }
                    if (checkPlausibleProps(propMap)) propertyCheck(n, k, propMap);
                    else {
                        System.out.println("There are no numbers with these properties.");
                    }
                } else if (temp.length > 1) {
                    n = Long.parseLong(temp[0]);
                    k = Integer.parseInt(temp[1]);
                    propertyCheck(n, k);
                } else if (temp.length == 1) {
                    try {
                        n = Long.parseLong(temp[0]);
                        propertyCheck(n);
                    } catch (Exception e) {
                        System.out.println("The first parameter should be a natural number or zero.");
                    }
                } else {
                    printRequests();
                }
            } catch (Exception e) {
                printRequests();
            }
        }
    }

    public static void printRequests() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - separate the parameters with one space;
                - enter 0 to exit.""");
    }

    public static boolean checkPlausibleProps(HashMap<String, Boolean> props) {
        if (props.containsKey("odd") && props.containsKey("even")) {
            System.out.println("The request contains mutually exclusive properties: [ODD, EVEN]");
            return false;
        } else if (props.containsKey("duck") && props.containsKey("spy")) {
            System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
            return false;
        } else if (props.containsKey("square") && props.containsKey("sunny")) {
            System.out.println("The request contains mutually exclusive properties: [SQUARE, SUNNY]");
            return false;
        }
        return true;
    }

    public static void propertyCheck(long n) {
        System.out.println();
        if (n == 0) {
            System.out.println("\nGoodbye!");
        } else if (n < 0) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else {
            System.out.printf("Properties of %d\n", n);
            System.out.println("        buzz: " + isBuzz(n));
            System.out.println("        duck: " + isDuck(n));
            System.out.println(" palindromic: " + isPalindromic(Long.toString(n)));
            System.out.println("      gapful: " + isGapful(n));
            System.out.println("         spy: " + isSpy(n));
            System.out.println("      square: " + isSquare(n));
            System.out.println("       sunny: " + isSunny(n));
            System.out.println("     jumping: " + isJumping(n));
            System.out.println("        even: " + isEven(n));
            System.out.println("         odd: " + isOdd(n));
        }
    }

    public static void propertyCheck(long n, int k) {
        System.out.println();
        if (k < 1) {
            System.out.println("The second parameter should be a natural number.");
            return;
        }
        while (k-- != 0) {
            String[] res = amazingNum(n);
            n = printNumProp(n, res);
        }
    }

    public static String[] amazingNum(long n) {
        int p = 0;
        String[] res = new String[20];
        if (isBuzz(n)) res[p++] = "buzz";
        if (isDuck(n)) res[p++] = "duck";
        if (isPalindromic(Long.toString(n))) res[p++] = "palindromic";
        if (isGapful(n)) res[p++] = "gapful";
        if (isSpy(n)) res[p++] = "spy";
        if (isSquare(n)) res[p++] = "square";
        if (isSunny(n)) res[p++] = "sunny";
        if (isJumping(n)) res[p++] = "jumping";
        if (isEven(n)) res[p++] = "even";
        if (isOdd(n)) res[p] = "odd";
        return res;
    }

    public static long printNumProp(long n, String[] res) {
        System.out.printf("%d is ", n++);
        for (int i = 0; !Objects.equals(res[i], null); i++) {
            System.out.print(res[i] + "");
            if (!Objects.equals(res[i + 1], null)) System.out.print(", ");
        }
        System.out.println();
        return n;
    }

    public static void propertyCheck(long n, int k, HashMap<String, Boolean> props) {
        if (k < 1) {
            System.out.println("The second parameter should be a natural number.");
            return;
        }
        System.out.println();
        String[] properties = {"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "EVEN", "ODD", "SQUARE", "SUNNY", "JUMPING"};

        ArrayList<String> wrongProp = new ArrayList<>();
        for (Map.Entry<String, Boolean> mapElement : props.entrySet()) {
            String tempProp = mapElement.getKey().toUpperCase();
            if (!(Arrays.asList(properties).contains(tempProp))) wrongProp.add(tempProp);
        }
        if (wrongProp.size() > 0) {
            if (wrongProp.size() == 1) System.out.println("The property " + wrongProp + " is wrong.");
            else System.out.println("The properties " + wrongProp + " are wrong.");
            System.out.println("Available properties: " + Arrays.toString(properties));
            return;
        }

        while (k != 0) {
            if (isPossible(props, n)) {
                k--;
                String[] res = amazingNum(n);
                n = printNumProp(n, res);
            } else {
                n++;
            }
        }
    }

    public static boolean isPossible(HashMap<String, Boolean> props, long n) {
        boolean flag = true;
        for (Map.Entry<String, Boolean> mapElement : props.entrySet()) {
            flag = flag && flagCheck(mapElement.getKey(), n);
        }
        return flag;
    }

    public static boolean flagCheck(String prop, long n) {
        return switch (prop.toLowerCase()) {
            case "buzz" -> isBuzz(n);
            case "duck" -> isDuck(n);
            case "palindromic" -> isPalindromic(String.valueOf(n));
            case "gapful" -> isGapful(n);
            case "spy" -> isSpy(n);
            case "square" -> isSquare(n);
            case "sunny" -> isSunny(n);
            case "jumping" -> isJumping(n);
            case "even" -> isEven(n);
            case "odd" -> isOdd(n);
            default -> false;
        };
    }

    public static boolean isBuzz(long n) {
        return (n % 10 == 7 || n % 7 == 0);
    }

    public static boolean isDuck(long n) {
        return Long.toString(n).indexOf("0") > 0;
    }

    public static boolean isPalindromic(String n) {
        int k = n.length();
        if (k == 0 || k == 1) return true;
        if (n.charAt(0) == n.charAt(k - 1)) return isPalindromic(n.substring(1, k - 1));
        else return false;
    }

    public static boolean isGapful(long n) {
        if (n / 100 == 0) return false;
        String temp = Long.toString(n);
        String divisor = temp.charAt(0) + String.valueOf(temp.charAt(temp.length() - 1));
        return n % Long.parseLong(divisor) == 0;
    }

    public static boolean isSpy(long n) {
        long sum = 0;
        long prod = 1;
        while (n > 0) {
            sum += (n % 10);
            prod *= (n % 10);
            n = (n / 10);
        }
        return sum == prod;
    }

    public static boolean isSquare(long n) {
        long min = 0;
        long max = n;
        while (min <= max) {
            long mid = min + (max - min) / 2;
            if (mid * mid == n) return true;
            if (mid * mid < n) min = mid + 1;
            else max = mid - 1;
        }
        return false;
    }

    public static boolean isSunny(long n) {
        return isSquare(n + 1);
    }

    public static boolean isEven(long n) {
        return (n % 2 == 0);
    }

    public static boolean isOdd(long n) {
        return (n % 2 == 1);
    }

    public static boolean isJumping(long n) {
        if (n / 10 == 0) return true;
        int remainder = (int) (n % 10);
        n = n / 10;
        while (n != 0) {
            int temp = (int) (n % 10);
            n = n / 10;
            if (Math.abs(temp - remainder) != 1) {
                return false;
            }
            remainder = temp;
        }
        return true;
    }
}
