import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab2 {
    public static void main(String[] args) {
        Map<Integer, Double> map;
        map = legendre(6);
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

    static Map<Integer, Double> legendre(int n) {
        Map<Integer, Double> map1 = new HashMap<>();
        Map<Integer, Double> map2 = new HashMap<>();
        Map<Integer, Double> map3 = new HashMap<>();
        map1.put(0, 1.0);
        map2.put(1, 1.0);
        double temp;
        if (n >= 2) {
            for (int i = 2; i <= n; i++) {
                for (Map.Entry<Integer, Double> entry : map2.entrySet()) {
                    map3.put(entry.getKey() + 1, entry.getValue() * (2 * n + 3) / (n + 2));
                }
                for (Map.Entry<Integer, Double> entry : map1.entrySet()) {
                    temp = map3.getOrDefault(entry.getKey(), 0.0);
                    map3.put(entry.getKey(), -entry.getValue() * (n + 1) / (n + 2) + temp);
                }
                map1 = map2;
                map2 = map3;
                map3 = new HashMap<>();
            }
        }
        return map2;
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
        for (Map.Entry<Integer, Double> e : map.entrySet()) {
            poly.put(e.getKey(), e.getValue());
        }
    }

}