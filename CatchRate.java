public class CatchRate {

    public static double calc(double baseCaptureRate, double cpm,
                              double ball, double berry, double curve,
                              double throwBonus, double typeMedal) {
        // とりあえず全部かけ合わせた倍率を作る
        double m = ball * berry * curve * throwBonus * typeMedal;

        // 1回あたりの「素の」入りやすさ（？）みたいなの
        double per = baseCaptureRate / (2.0 * cpm);

        // 0〜1におさめた方が良さそうだけど、
        // とりあえず今回はそのまま（初心者なので様子見…）
        // if (per < 0) per = 0; if (per > 1) per = 1; // ←必要なら解放

        // 最後に確率を計算
        double prob = 1.0 - Math.pow(1.0 - per, m);
        return prob;
    }

    // 何回か投げたら1回は入る確率（独立試行の近似）
    public static double cumulative(double perThrowProb, int times) {
        if (times <= 0) return 0.0;
        return 1.0 - Math.pow(1.0 - perThrowProb, times);
    }

    public static void main(String[] args) {
        double bcr = 0.20;      // 基礎捕獲率 20%
        double cpm = 0.7317;    // Lv30 くらいの CPM と聞いた（要確認）

        double ball = 1.5;      // スーパーボール
        double berry = 1.5;     // ズリのみ
        double curve = 1.7;     // カーブ投げ
        double hit = 2.0;       // Excellent を想定（だいたい）
        double medal = 1.3;     // 金メダル

        double p = calc(bcr, cpm, ball, berry, curve, hit, medal);

        System.out.println("この1投の捕獲率(0〜1): " + p);
        System.out.println("パーセント表示: " + (p * 100) + "%");

        // 3回連続で投げたらどれくらい入る？（外しても次を投げる想定）
        int times = 3;
        double p3 = cumulative(p, times);
        System.out.println(times + "回投げた時に1回は入る確率: " + (p3 * 100) + "%");
    }
}