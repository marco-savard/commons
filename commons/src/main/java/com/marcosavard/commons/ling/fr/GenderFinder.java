package com.marcosavard.commons.ling.fr;

public class GenderFinder {

  public boolean isMasculine(String word) {
    boolean masculine = true;
    GenderRule[] rules = new GenderRule[] { //
        new PrefixRule(), //
        new EndingPithequeRule(), //
        // new EndingCephaleRule(), //
        new EndingGrammeRule(), //
        new EndingGrapheRule(), //
        new EndingOptereRule(), //
        new EndingPhylleRule(), //
        new EndingThermeRule(), //
        new EndingZoaireRule(), //
        new EndingAisonRule(), //
        new EndingDermeRule(), //
        new EndingIntheRule(), //
        new EndingLitheRule(), //
        new EndingMetreRule(), //
        new EndingOxydeRule(), //
        new EndingPhaleRule(), //
        new EndingPhareRule(), //
        new EndingPheneRule(), //
        new EndingPhoneRule(), //
        new EndingPhoreRule(), //
        new EndingPhyteRule(), //
        new EndingSaureRule(), //
        new EndingScopeRule(), //
        new EndingTexteRule(), //
        new EndingThemeRule(), //
        new EndingToireRule(), //
        new EndingTropeRule(), //
        new EndingCeteRule(), //
        new EndingCideRule(), //
        new EndingCyteRule(), //
        new EndingGeneRule(), //
        new EndingGoneRule(), //
        new EndingIomeRule(), //
        new EndingIsmeRule(), //
        new EndingNomeRule(), //
        new EndingNymeRule(), //
        new EndingOideRule(), //
        new EndingPodeRule(), //
        new EndingSionRule(), //
        // new EndingSsonRule(), //
        new EndingTionRule(), //
        // new EndingTomeRule(), //
        new EndingTypeRule(), //
        new EndingXionRule(), //
        new EndingAgeRule(), //
        new EndingYleRule(), //
        new EndingTeRule(), //
        new GeneralRule() //
    };

    for (GenderRule rule : rules) {
      if (rule.applies(word)) {
        masculine = rule.isMasculine(word);
        break;
      }
    }

    return masculine;
  }

  // inner classes

  private static abstract class GenderRule {
    protected boolean applies(String word) {
      return true;
    }

    protected abstract boolean isMasculine(String word);
  }

  private static class PrefixRule extends GenderRule {
    protected boolean applies(String word) {
      boolean hasPrefix = word.startsWith("abat-");
      hasPrefix = hasPrefix || word.startsWith("aide-");
      hasPrefix = hasPrefix || word.startsWith("allume-");
      hasPrefix = hasPrefix || word.startsWith("amuse-");
      hasPrefix = hasPrefix || word.startsWith("appuie-");
      hasPrefix = hasPrefix || word.startsWith("arrache-");
      hasPrefix = hasPrefix || word.startsWith("attrape-");
      hasPrefix = hasPrefix || word.startsWith("bouche-");
      hasPrefix = hasPrefix || word.startsWith("bourre-");
      hasPrefix = hasPrefix || word.startsWith("brise-");
      hasPrefix = hasPrefix || word.startsWith("brule-");
      hasPrefix = hasPrefix || word.startsWith("cache-");
      hasPrefix = hasPrefix || word.startsWith("capte-");
      hasPrefix = hasPrefix || word.startsWith("casse-");
      hasPrefix = hasPrefix || word.startsWith("chasse-");
      hasPrefix = hasPrefix || word.startsWith("chauffe-");
      hasPrefix = hasPrefix || word.startsWith("compte-");
      hasPrefix = hasPrefix || word.startsWith("coupe-");
      hasPrefix = hasPrefix || word.startsWith("couvre-");
      hasPrefix = hasPrefix || word.startsWith("croche-");
      hasPrefix = hasPrefix || word.startsWith("croque-");
      hasPrefix = hasPrefix || word.startsWith("crève-");
      hasPrefix = hasPrefix || word.startsWith("cure-");
      hasPrefix = hasPrefix || word.startsWith("emporte-");
      hasPrefix = hasPrefix || word.startsWith("essuie-");
      hasPrefix = hasPrefix || word.startsWith("épluche-");
      hasPrefix = hasPrefix || word.startsWith("fixe-");
      hasPrefix = hasPrefix || word.startsWith("fume-");
      hasPrefix = hasPrefix || word.startsWith("garde-");
      hasPrefix = hasPrefix || word.startsWith("gobe-");
      hasPrefix = hasPrefix || word.startsWith("gâte-");
      hasPrefix = hasPrefix || word.startsWith("hache-");
      hasPrefix = hasPrefix || word.startsWith("hors-");
      hasPrefix = hasPrefix || word.startsWith("lance-");
      hasPrefix = hasPrefix || word.startsWith("lave-");
      hasPrefix = hasPrefix || word.startsWith("lâcher-");
      hasPrefix = hasPrefix || word.startsWith("lèche-");
      hasPrefix = hasPrefix || word.startsWith("lève-");
      hasPrefix = hasPrefix || word.startsWith("marque-");
      hasPrefix = hasPrefix || word.startsWith("monte-");
      hasPrefix = hasPrefix || word.startsWith("ouvre-");

      hasPrefix = hasPrefix || word.startsWith("pare-");
      hasPrefix = hasPrefix || word.startsWith("passe-");
      hasPrefix = hasPrefix || word.startsWith("pense-");
      hasPrefix = hasPrefix || word.startsWith("perce-");
      hasPrefix = hasPrefix || word.startsWith("pince-");
      hasPrefix = hasPrefix || word.startsWith("pique-");
      hasPrefix = hasPrefix || word.startsWith("pleure-");
      hasPrefix = hasPrefix || word.startsWith("porte-");
      hasPrefix = hasPrefix || word.startsWith("presse-");
      hasPrefix = hasPrefix || word.startsWith("protège-");
      hasPrefix = hasPrefix || word.startsWith("pèse-");

      hasPrefix = hasPrefix || word.startsWith("rabat-");
      hasPrefix = hasPrefix || word.startsWith("ramasse-");
      hasPrefix = hasPrefix || word.startsWith("rase-");
      hasPrefix = hasPrefix || word.startsWith("remonte-");
      hasPrefix = hasPrefix || word.startsWith("remue-");
      hasPrefix = hasPrefix || word.startsWith("repose-");
      hasPrefix = hasPrefix || word.startsWith("rince-");
      hasPrefix = hasPrefix || word.startsWith("ruine-");

      hasPrefix = hasPrefix || word.startsWith("saute-");
      hasPrefix = hasPrefix || word.startsWith("serre-");
      hasPrefix = hasPrefix || word.startsWith("sèche-");

      hasPrefix = hasPrefix || word.startsWith("taille-");
      hasPrefix = hasPrefix || word.startsWith("tire-");
      hasPrefix = hasPrefix || word.startsWith("tourne-");
      hasPrefix = hasPrefix || word.startsWith("traine-");
      hasPrefix = hasPrefix || word.startsWith("trousse-");
      hasPrefix = hasPrefix || word.startsWith("tue-");
      hasPrefix = hasPrefix || word.startsWith("vide-");

      return hasPrefix;
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPithequeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("pithèque");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingCephaleRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("céphale");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingGrammeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("gramme");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingGrapheRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("graphe");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingOptereRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("optère");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPhylleRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phylle");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }


  private static class EndingThermeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("therme");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingZoaireRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("zoaire");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingAisonRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("aison");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }

  private static class EndingDermeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("derme");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingIntheRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("inthe");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingLitheRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("lithe");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingMetreRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("mètre");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingOxydeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("oxyde");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPhaleRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phale");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPhareRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phare");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPheneRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phène");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPhoneRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phone");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPhoreRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phore");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingPhyteRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("phyte");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingSaureRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("saure");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingScopeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("scope");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingTexteRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("texte");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingThemeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("thème");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingToireRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("toire");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingTropeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("trope");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingCeteRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("cète");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingCideRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("cide");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingCyteRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("cyte");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingGeneRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("gène");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingGoneRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("gone");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingIomeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("iome");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingIsmeRule extends GenderRule {
    protected boolean applies(String word) {
      boolean endsInIsme = word.endsWith("isme") || word.endsWith("ïsme");
      return endsInIsme;
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingNomeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("nome");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingNymeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("nyme");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingOideRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("oïde");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }


  private static class EndingPodeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("pode");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingSionRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("sion");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }

  private static class EndingSsonRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("sson");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }


  private static class EndingTionRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("tion");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }

  private static class EndingTomeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("tome");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }

  private static class EndingTypeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("type");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingXionRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("xion");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }

  private static class EndingAgeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("age");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingYleRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("yle");
    }

    protected boolean isMasculine(String word) {
      return true;
    }
  }

  private static class EndingTeRule extends GenderRule {
    protected boolean applies(String word) {
      return word.endsWith("té");
    }

    protected boolean isMasculine(String word) {
      return false;
    }
  }

  private static class GeneralRule extends GenderRule {
    @Override
    protected boolean isMasculine(String word) {
      boolean masculine = !word.endsWith("e");
      return masculine;
    }

  }

}
