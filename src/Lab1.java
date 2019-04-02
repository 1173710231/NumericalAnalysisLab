import java.util.ArrayList;
import java.util.List;


public class Lab1 {

    public static void main(String[] args) {
        List<Double> X = new ArrayList<>();//插值函数的xk
        List<Double> Fx = new ArrayList<>();//插值函数的f(xk)

        int[] N1 = {5, 10, 20};//问题1 f1分N段
        double[] x1 = {0.75, 1.75, 2.75, 3.75, 4.75};//问题1 所求x的值
        int[] N2 = {5, 10, 20};//问题2 f1分N段
        double[] x2 = {-0.95, -0.05, 0.05, 0.95};//问题2 所求x的值
        double[] x3 = {5, 50, 80, 115};//问题3 所求x的值
        double[][] x30 = {{1, 4, 9}, {36, 49, 64}, {100, 121, 144}, {169, 196, 225}};//问题3 给出x的点

        //region 问题1
        System.out.println("问题1");
        System.out.println("对于f(x) = 1/(1+x^2)");
        for (int i = 0; i < N1.length; i++) {
            for (int j = 0; j < x1.length; j++) {
                createP1x(-5, 5, N1[i], X, Fx);
                System.out.printf("在[-5, 5]区间, 进行%d等分, f(%f) = %f, P(%f) = %f\n",
                        N1[i], x1[j], f1(x1[j]), x1[j], foo(X, Fx, x1[j]));
            }
        }
        System.out.println();

        System.out.println("对于f(x) = e^x");
        for (int i = 0; i < N2.length; i++) {
            for (int j = 0; j < x2.length; j++) {
                createP2x(-1, 1, N2[i], X, Fx);
                System.out.printf("在[-1, 1]区间, 进行%d等分, f(%f) = %f, P(%f) = %f\n",
                        N2[i], x2[j], f2(x2[j]), x2[j], foo(X, Fx, x2[j]));
            }
        }
        System.out.println();
        //endregion

        //region 问题2
        System.out.println("\n问题2");
        System.out.println("问题1");
        System.out.println("对于f(x) = 1/(1+x^2)");
        for (int i = 0; i < N1.length; i++) {
            for (int j = 0; j < x1.length; j++) {
                createP1x(-1, 1, N1[i], X, Fx);
                System.out.printf("在[-1, 1]区间, 进行%d等分, f(%f) = %f, P(%f) = %f\n",
                        N1[i], x1[j], f1(x1[j]), x1[j], foo(X, Fx, x1[j]));
            }
        }
        System.out.println();

        System.out.println("对于f(x) = e^x");
        for (int i = 0; i < N2.length; i++) {
            for (int j = 0; j < x2.length; j++) {
                createP2x(-5, 5, N2[i], X, Fx);
                System.out.printf("在[-5, 5]区间, 进行%d等分, f(%f) = %f, P(%f) = %f\n",
                        N2[i], x2[j], f2(x2[j]), x2[j], foo(X, Fx, x2[j]));
            }
        }
        System.out.println();
        //endregion

        //region 问题3
        System.out.println("\n问题3");
        System.out.println("对于f(x) = 1/(1+x^2)");
        for (int i = 0; i < N1.length; i++) {
            for (int j = 0; j < x1.length; j++) {
                createP1xByCos(N1[i], X, Fx);
                System.out.printf("在[-1, 1]区间, 划分%d段, f(%f) = %f, P(%f) = %f\n",
                        N1[i], x1[j], f1(x1[j]), x1[j], foo(X, Fx, x1[j]));
            }
        }
        System.out.println();

        System.out.println("对于f(x) = e^x");
        for (int i = 0; i < N2.length; i++) {
            for (int j = 0; j < x2.length; j++) {
                createP2xByCos(N2[i], X, Fx);
                System.out.printf("在[-5, 5]区间, 划分%d段, f(%f) = %f, P(%f) = %f\n",
                        N2[i], x2[j], f2(x2[j]), x2[j], foo(X, Fx, x2[j]));
            }
        }
        System.out.println();
        //endregion

        //region 问题4
        System.out.println("\n问题4");
        System.out.println("对于f(x) = √x");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < x3.length; j++) {
                creatP3x(x30[i][0], x30[i][1], x30[i][2], X, Fx);
                System.out.printf("x0 = %f, x1 = %f, x2 = %f, 此时f(%f) = %f, P(%f) = %f\n",
                        x30[i][0], x30[i][1], x30[i][2], x3[j], f3(x3[j]), x3[j], foo(X, Fx, x3[j]));
            }

        }
        //endregion

    }

    static double foo(List<Double> X, List<Double> Fx, Double x) {
        double y = 0.0;
        int k = 0;
        int n = X.size() - 1;
        double l;
        while (k <= n) {
            l = 1.0;
            for (int j = 0; j <= n; j++) {
                if (k != j) {
                    l = l * (x - X.get(j)) / (X.get(k) - X.get(j));
                }
            }
            y = y + l * Fx.get(k);
            k++;
        }
        return y;
    }

    static double f1(double x) {
        return 1 / (1 + x * x);
    }

    static double f2(double x) {
        return Math.exp(x);
    }

    static double f3(double x) {
        return Math.sqrt(x);
    }

    static void createP1x(double start, double end, int n, List<Double> X, List<Double> Fx) {
        double h = (start - end) / n;
        X.clear();
        Fx.clear();
        for (int i = 0; i <= n; i++) {
            double xi = start + i * h;
            X.add(xi);
            Fx.add(f1(xi));
        }
    }

    static void createP2x(double start, double end, int n, List<Double> X, List<Double> Fx) {
        double h = (start - end) / n;
        X.clear();
        Fx.clear();
        for (int i = 0; i <= n; i++) {
            double xi = start + i * h;
            X.add(xi);
            Fx.add(f2(xi));
        }
    }

    static void createP1xByCos(int n, List<Double> X, List<Double> Fx) {
        X.clear();
        Fx.clear();
        double h = 1.0 / (2 * (n + 1));
        for (int i = 0; i <= n; i++) {
            double xi = Math.cos(2 * (i + 1) * h);
            X.add(xi);
            Fx.add(f1(xi));
        }
    }

    static void createP2xByCos(int n, List<Double> X, List<Double> Fx) {
        X.clear();
        Fx.clear();
        double h = 1.0 / (2 * (n + 1));
        for (int i = 0; i <= n; i++) {
            double xi = Math.cos(2 * (i + 1) * h);
            X.add(xi);
            Fx.add(f2(xi));
        }
    }

    static void creatP3x(double a1, double a2, double a3, List<Double> X, List<Double> Fx) {
        X.clear();
        Fx.clear();
        X.add(a1);
        X.add(a2);
        X.add(a3);
        Fx.add(f3(a1));
        Fx.add(f3(a2));
        Fx.add(f3(a3));
    }
}
