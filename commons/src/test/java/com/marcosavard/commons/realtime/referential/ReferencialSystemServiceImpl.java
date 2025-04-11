package com.marcosavard.commons.realtime.referential;

class ReferencialSystemServiceImpl implements ReferencialSystemService {
    @Override
    public double calculate(double value, short[] bh) {
        bh[0] = (short) value;
        double a = (bh[0] / 10_000.0);
        double b = Math.exp(a);
        bh[1] = (short) (a * 100);
        bh[2] = (short) (b * 100);
        bh[3] = (short) (10 / (short) b);
        return 0;
    }
}
