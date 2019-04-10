import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab2 {
    public static void main(String[] args) {
        try {
            PolyHelper polyHelper = new PolyHelper(Hermite(6).get(6));
            System.out.println(Newton(polyHelper, 1e-6,1e-6, 200, 0.4));;
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    static double Newton(Function f, double eps1, double eps2, int N, double x0) throws Exception {
        int n = 1;
        double F;
        double DF;
        double x1;
        double Tol;
        while (n <= N) {
            F = f.F(x0);
            DF = f.DF(x0);
            if (Math.abs(F) < eps1) {
                return x0;
            } else if (Math.abs(DF) < eps2) {
                throw new Exception();
            }
            x1 = x0 - F / DF;
            Tol = Math.abs(x1 - x0);
            if (Tol < eps1) {
                return x1;
            }
            n++;
            x0 = x1;
        }
        throw new Exception();
    }

    static List<Map<Integer, Double>> Legendre(int n) {
        List<Map<Integer, Double>> maps = new ArrayList<>();
        maps.add(new HashMap<>());
        maps.get(0).put(0, 1.0);
        maps.add(new HashMap<>());
        maps.get(1).put(1, 1.0);
        double temp;
        if (n >= 2) {
            for (int i = 0; i <= n - 2; i++) {
                maps.add(new HashMap<>());
                for (Map.Entry<Integer, Double> entry : maps.get(i + 1).entrySet()) {
                    maps.get(i + 2).put(entry.getKey() + 1, entry.getValue() * (2 * i + 3) / (i + 2));
                }
                for (Map.Entry<Integer, Double> entry : maps.get(i).entrySet()) {
                    temp = maps.get(i + 2).getOrDefault(entry.getKey(), 0.0);
                    maps.get(i + 2).put(entry.getKey(), temp - entry.getValue() * (i + 1) / (i + 2));
                }
            }
        }
        return maps;
    }

    static List<Map<Integer, Double>> Chebyshev(int n) {
        List<Map<Integer, Double>> maps = new ArrayList<>();
        maps.add(new HashMap<>());
        maps.get(0).put(0, 1.0);
        maps.add(new HashMap<>());
        maps.get(1).put(1, 1.0);
        double temp;
        if (n >= 2) {
            for (int i = 0; i <= n - 2; i++) {
                maps.add(new HashMap<>());
                for (Map.Entry<Integer, Double> entry : maps.get(i + 1).entrySet()) {
                    maps.get(i + 2).put(entry.getKey() + 1, entry.getValue() * 2);
                }
                for (Map.Entry<Integer, Double> entry : maps.get(i).entrySet()) {
                    temp = maps.get(i + 2).getOrDefault(entry.getKey(), 0.0);
                    maps.get(i + 2).put(entry.getKey(), temp - entry.getValue());
                }
            }
        }
        return maps;
    }

    static List<Map<Integer, Double>> Laguerre(int n) {
        List<Map<Integer, Double>> maps = new ArrayList<>();
        maps.add(new HashMap<>());
        maps.get(0).put(0, 1.0);
        maps.add(new HashMap<>());
        maps.get(1).put(1, -1.0);
        maps.get(1).put(0,1.0);
        double temp;
        if (n >= 2) {
            for (int i = 0; i <= n - 2; i++) {
                maps.add(new HashMap<>());
                for (Map.Entry<Integer, Double> entry : maps.get(i + 1).entrySet()) {
                    temp = maps.get(i + 2).getOrDefault(entry.getKey() + 1, 0.0);
                    maps.get(i + 2).put(entry.getKey() + 1, temp - entry.getValue());
                    temp = maps.get(i + 2).getOrDefault(entry.getKey(), 0.0);
                    maps.get(i + 2).put(entry.getKey(), temp + entry.getValue() * (2 * i + 3));
                }
                for (Map.Entry<Integer, Double> entry : maps.get(i).entrySet()) {
                    temp = maps.get(i + 2).getOrDefault(entry.getKey(), 0.0);
                    maps.get(i + 2).put(entry.getKey(), temp - entry.getValue() * (i + 1) * (i + 1));
                }
            }
        }
        return maps;
    }

    static List<Map<Integer, Double>> Hermite(int n) {
        List<Map<Integer, Double>> maps = new ArrayList<>();
        maps.add(new HashMap<>());
        maps.get(0).put(0, 1.0);
        maps.add(new HashMap<>());
        maps.get(1).put(1, 2.0);
        double temp;
        if (n >= 2) {
            for (int i = 0; i <= n - 2; i++) {
                maps.add(new HashMap<>());
                for (Map.Entry<Integer, Double> entry : maps.get(i + 1).entrySet()) {
                    temp = maps.get(i + 2).getOrDefault(entry.getKey()+1, 0.0);
                    maps.get(i + 2).put(entry.getKey() + 1, entry.getValue() * 2);
                }
                for (Map.Entry<Integer, Double> entry : maps.get(i).entrySet()) {
                    temp = maps.get(i + 2).getOrDefault(entry.getKey(), 0.0);
                    maps.get(i + 2).put(entry.getKey(), temp - entry.getValue() * 2 * (i + 1));
                }
            }
        }
        return maps;
    }

    static void print(List<Map<Integer, Double>> list, String name) {
        for (Map<Integer, Double> map : list) {
            System.out.print(name + "(x) = ");
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                System.out.print(entry.getValue() + "x^" + entry.getKey() + " + ");
            }
            System.out.println();

        }
    }
}

interface Function {
    double F(double x);

    double DF(double x);
}

//f(x) = cosx - x
class F1 implements Function {
    public double F(double x) {
        return Math.cos(x) - x;
    }

    public double DF(double x) {
        return -Math.sin(x) - 1;
    }
}

//f(x) = e^-x - sinx
class F2 implements Function {
    public double F(double x) {
        return Math.exp(-x) - Math.sin(x);
    }

    public double DF(double x) {
        return -Math.exp(-x) - Math.cos(x);
    }
}

//f(x) = x - e^-x
class F3 implements Function {
    public double F(double x) {
        return x - Math.exp(-x);
    }

    public double DF(double x) {
        return 1 + Math.exp(-x);
    }
}

//f(x) = x^2 + 2xe^-x + e^-2x
class F4 implements Function {
    public double F(double x) {
        return x * x + 2 * x * Math.exp(-x) + Math.exp(-2 * x);
    }

    public double DF(double x) {
        return 2 * x - 2 * Math.exp(-2 * x) + 2 * Math.exp(-x) + 2 * x * Math.exp(-x);
    }
}

//多项式
class PolyHelper implements Function {
    private Map<Integer, Double> poly;

    public double F(double x) {
        double result = 0;
        for (Map.Entry<Integer, Double> entry : poly.entrySet()) {
            result += Math.pow(x, entry.getKey()) * entry.getValue();
        }
        return result;
    }

    public double DF(double x) {
        double result = 0;
        for (Map.Entry<Integer, Double> entry : poly.entrySet()) {
            result += Math.pow(x, entry.getKey() - 1) * entry.getValue() * entry.getKey();
        }
        return result;
    }

    public PolyHelper(Map<Integer, Double> map) {
        poly = new HashMap<>();
        for (Map.Entry<Integer, Double> e : map.entrySet()) {
            poly.put(e.getKey(), e.getValue());
        }
    }

}