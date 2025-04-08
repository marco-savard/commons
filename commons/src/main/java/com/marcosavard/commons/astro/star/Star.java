package com.marcosavard.commons.astro.star;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.space.SpaceCoordinate.Declination;
import com.marcosavard.commons.astro.space.SpaceCoordinate.RightAscension;

import static com.marcosavard.commons.astro.star.Constellation.*;

public enum Star {
    //Zodiac
    HAMAL(ARIES, Declination.of(23.53), RightAscension.ofHours(2.12),2,65.8),
    ALDEBARAN(TAURUS, Declination.of(16.51), RightAscension.ofHours(4.6),0.85,65.3),
    ELNATH(TAURUS, Declination.of(28.6), RightAscension.ofHours(5.44),	1.65, 131),
    CASTOR(GEMINI, Declination.of(31.89), RightAscension.ofHours(7.58),1.58,51.5),
    POLLUX(GEMINI, Declination.of(28.03), RightAscension.ofHours(7.76), 1.14, 33.7),
    ALHENA(GEMINI, Declination.of(16.41), RightAscension.ofHours(6.63),	1.93, 105),
    REGULUS(LEO, Declination.of(11.97), RightAscension.ofHours(10.14),	1.35, 79.3),
    DENEBOLA(LEO, Declination.of(14.57), RightAscension.ofHours(11.82),	2.14, 35.9),
    ALGIEBA(LEO, Declination.of(19.84), RightAscension.ofHours(10.33),	2.01, 130),
    SPICA(VIRGO, Declination.of(-11.37), RightAscension.ofHours(13.42), 0.97, 250),
    ZUBENELGENUBI(LIBRA, Declination.of(-16.04), RightAscension.ofHours(14.85), 2.75, 77.3),
    ZUBENESCHAMALI(LIBRA, Declination.of(-9.38), RightAscension.ofHours(15.28), 2.61, 160),
    ANTARES(SCORPIO, Declination.of(-26.43), RightAscension.ofHours(16.49), 0.96, 550),
    SHAULA(SCORPIO, Declination.of(-37.10), RightAscension.ofHours(17.56),	1.62, 570),
    KAUS_AUSTRALIS(SAGITTARIUS, Declination.of(-34.38), RightAscension.ofHours(18.40), 1.85, 143),
    DENEB_ALGEDI(CAPRICORNUS, Declination.of(-16.53), RightAscension.ofHours(21.78), 2.85, 39),
    SADALSUUD(AQUARIUS, Declination.of(-5.45), RightAscension.ofHours(21.68), 2.91, 540),
    ALRESCHA(PISCES, Declination.of(2), RightAscension.ofHours(2.03), 3.82, 139),

    //Northern
    ALIOTH(URSUS_MAJOR, Declination.of(55.96), RightAscension.ofHours(12.90),	1.76, 81),
    DUBHE(URSUS_MAJOR, Declination.of(61.75), RightAscension.ofHours(11.06),	1.79, 123),
    ALKAID(URSUS_MAJOR, Declination.of(49.31), RightAscension.ofHours(13.79),	1.85, 104),
    MIZAR(URSUS_MAJOR, Declination.of(54.93), RightAscension.ofHours(13.40),	2.23, 83),
    MERAK(URSUS_MAJOR, Declination.of(56.38), RightAscension.ofHours(11.90),	2.37, 79),
    PHECDA(URSUS_MAJOR, Declination.of(53.69), RightAscension.ofHours(11.90),	2.43, 83),
    MEGREZ(URSUS_MAJOR, Declination.of(57.03), RightAscension.ofHours(12.26),	3.32, 80),
    ALCOR(URSUS_MAJOR, Declination.of(54.99), RightAscension.ofHours(13.42),	3.99, 83),
    POLARIS(URSUS_MINOR, Declination.of(89.26), RightAscension.ofHours(2.53),	1.98, 433),
    KOCHAB(URSUS_MINOR, Declination.of(74.15), RightAscension.ofHours(14.85),	2.07, 	131),
    PHERKAD(URSUS_MINOR, Declination.of(71.83), RightAscension.ofHours(15.35),	3.05, 	487),
    YILDUN(URSUS_MINOR, Declination.of(86.59), RightAscension.ofHours(17.54),	4.36, 	183),

    ARCTURUS(BOOTES, Declination.of(19.18), RightAscension.ofHours(14.26),-0.05,36.7),
    IZAR(BOOTES, Declination.of(27.07), RightAscension.ofHours(14.74),2.29,202),
    VEGA(LYRA, Declination.of(38.79), RightAscension.ofHours(18.62),0.03,25.3),
    CAPELLA(AURIGA, Declination.of(5.99), RightAscension.ofHours(5.28),	0.08, 42.0),
    PROCYON(CANIS_MINOR, Declination.of(5.22), RightAscension.ofHours(7.66),	0.4, 11.4),
    BETELGEUSE(ORION, Declination.of(7.41), RightAscension.ofHours(5.92),	0.42, 548),
    BELLATRIX(ORION, Declination.of(6.37), RightAscension.ofHours(5.42),	1.64, 243),
    ALNILAM(ORION, Declination.of(-1.2), RightAscension.ofHours(5.6),	1.69, 1975),
    MINTAKA(ORION, Declination.of(-0.30), RightAscension.ofHours(5.60),	2.23, 900),

    ALTAIR(AQUILA, Declination.of(8.87), RightAscension.ofHours(19.84),	0.76, 16.7),
    DENEB(CYGNUS, Declination.of(45.28), RightAscension.ofHours(20.69),	1.25, 2615),
    SADR(CYGNUS, Declination.of(45.28), RightAscension.ofHours(20.37),	2.23, 1800),
    CAPH(CASSIOPEIA, Declination.of(59.15), RightAscension.ofHours(0.15),	2.28, 54.5),
    SCHEDAR(CASSIOPEIA, Declination.of(56.54), RightAscension.ofHours(0.68),	2.24, 228),
    MIRFAK(PERSEUS, Declination.of(49.87), RightAscension.ofHours(3.41),	1.79, 510),
    ALGOL(PERSEUS, Declination.of(40.96), RightAscension.ofHours(3.14),	2.12, 92.8),
    MENKALINAN(AURIGA, Declination.of(44.93), RightAscension.ofHours(6),	1.90, 81.1),
    MIRACH(ANDROMEDA, Declination.of(35.62), RightAscension.ofHours(1.16),	2.05, 197),
    ALPHERATZ(ANDROMEDA, Declination.of(29.09), RightAscension.ofHours(0.14),	2.06, 97),
    ALMACH(ANDROMEDA, Declination.of(42.33), RightAscension.ofHours(2.10),	2.26, 350),

    GIENAH(CORVUS, Declination.of(33.80), RightAscension.ofHours(12.26),	2.59, 155),
    ELTANIN(DRACO, Declination.of(51.49), RightAscension.ofHours(17.77),	2.23, 154),
    ALPHECCA(CORONA_BOREALIS, Declination.of(26.71), RightAscension.ofHours(15.58), 2.23, 75),


    //Southern
    SIRIUS(CANIS_MAJOR, Declination.of(-16.71), RightAscension.ofHours(6.75),	-1.46, 8.6),
    CANOPUS(CARINA, Declination.of(-52.70), RightAscension.ofHours(6.40),	-0.72, 310),
    AVIOR(CARINA, Declination.of(-59.52), RightAscension.ofHours(8.38),	1.86, 630),
    ALPHA_CENTAURI(CENTAURUS, Declination.of(-60.83), RightAscension.ofHours(14.67),	-0.27, 4.37),
    ACHERNAR(ERIDANUS, Declination.of(-57.25), RightAscension.ofHours(1.63),	0.46, 139),
    ACRUX(CRUX, Declination.of(-63.10), RightAscension.ofHours(12.45),	0.76, 321),
    BETA_CENTAURI(CENTAURUS, Declination.of(-60.37), RightAscension.ofHours(14.07),	0.61, 390),
    HADAR(CENTAURUS, Declination.of(-60.37), RightAscension.ofHours(14.07),	0.61, 390),
    FOMALHAUT(PISCES_AUSTRINUS, Declination.of(	-29.62), RightAscension.ofHours(22.96),	1.16, 25.1),
    MIMOSA(CRUX, Declination.of(-59.69), RightAscension.ofHours(12.80),	1.25, 280),
    ADHARA(CANIS_MAJOR, Declination.of(-28.97), RightAscension.ofHours(6.98),	1.50, 430),
    GACRUX(CRUX, Declination.of(-57.12), RightAscension.ofHours(12.52),	1.63, 88),
    ALNAIR(GRUS, Declination.of(-46.96), RightAscension.ofHours(22.14), 1.73, 101),
    PEACOCK(PAVO, Declination.of(-56.74), RightAscension.ofHours(20.43), 1.94, 179),

    M13(HERCULES, Declination.of(36, 28, 0), RightAscension.of(16, 41, 42), 5.8, 22.2),
    ;

    private Constellation constellation;
    private Declination declination;
    private RightAscension ra;
    private double magnitude, dist;

    Star(Constellation constellation, Declination dec, RightAscension ra, double mag, double dist) {
        this.constellation = constellation;
        this.declination = dec;
        this.ra = ra;
        this.magnitude = mag;
        this.dist = dist;
    }

    public SpaceCoordinate coordinate() {
        return SpaceCoordinate.of(ra, declination);
    }
}
