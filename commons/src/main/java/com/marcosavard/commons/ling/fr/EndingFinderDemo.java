package com.marcosavard.commons.ling.fr;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.marcosavard.commons.ling.Noun;
import com.marcosavard.commons.ling.NounReader;
import com.marcosavard.commons.math.type.Percent;

public class EndingFinderDemo {

  public static void main(String[] args) {
    try {
      Map<String, Noun> allNouns = loadDictionary();
      EndingFinder finder = new EndingFinder();
      List<String> wordsE = finder.getWordsEndingIn(allNouns, "e");
      computeGenders(allNouns, wordsE, wordsE, "e", true);

      // PREFIX
      removeWordsWithPrefix(allNouns, finder, wordsE, "abat-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "aide-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "appuie-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "bourre-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "cache-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "capte-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "casse-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "chasse-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "coupe-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "cure-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "emporte-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "entre-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "essuie-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "fume-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "garde-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "gobe-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "hors-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "lâcher-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "lance-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "lave-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "ouvre-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "pare-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "pince-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "porte-", true);

      removeWordsWithPrefix(allNouns, finder, wordsE, "ramasse-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "remonte-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "remue-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "sèche-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "taille-", true);
      removeWordsWithPrefix(allNouns, finder, wordsE, "tire-", true);

      // SUFFIX

      // ae
      removeWordsWithSuffix(allNouns, finder, wordsE, "ae", true);

      // be

      removeWordsWithSuffix(allNouns, finder, wordsE, "abe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "bbe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "èbe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ibe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lbe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ombe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ambe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mbe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "obe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rbe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ube", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ybe", true);

      // ce
      removeWordsWithSuffix(allNouns, finder, wordsE, "ace", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nce", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ice", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rce", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uce", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "oce", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "èce", false);

      // de
      removeWordsWithSuffix(allNouns, finder, wordsE, "ade", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rde", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nde", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ode", true); // pode
      removeWordsWithSuffix(allNouns, finder, wordsE, "yde", true); // hyde, xyde
      removeWordsWithSuffix(allNouns, finder, wordsE, "pède", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ède", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ude", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ide", true); // cide
      removeWordsWithSuffix(allNouns, finder, wordsE, "oïde", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ïde", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lde", false);

      // ee
      removeWordsWithSuffix(allNouns, finder, wordsE, "ee", true);

      // ée
      removeWordsWithSuffix(allNouns, finder, wordsE, "cée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "née", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "sée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "hée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "dée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "bée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "pée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "vée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "iée", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mée", false);

      // fe
      removeWordsWithSuffix(allNouns, finder, wordsE, "ife", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ffe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "afe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fe", true);


      // ge
      removeWordsWithSuffix(allNouns, finder, wordsE, "ange", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "inge", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "âge", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uge", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ige", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "oge", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "age", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rge", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ège", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lge", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "onge", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nge", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "dge", true);

      // he
      removeWordsWithSuffix(allNouns, finder, wordsE, "graphe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "che", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ithe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "inthe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "the", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ghe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rrhe", true);


      // ie
      removeWordsWithSuffix(allNouns, finder, wordsE, "rie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "hie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "pie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "sie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "aie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "die", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "xie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "cie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "bie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "oie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "zie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "vie", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fie", true); // selfie
      removeWordsWithSuffix(allNouns, finder, wordsE, "kie", false);



      // ke
      removeWordsWithSuffix(allNouns, finder, wordsE, "ake", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ke", true);

      // le
      removeWordsWithSuffix(allNouns, finder, wordsE, "èle", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phale", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gale", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ale", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "bule", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ule", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lle", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "bole", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ole", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "cle", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ble", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ôle", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gle", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ple", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "yle", true); // dyle, xyle
      removeWordsWithSuffix(allNouns, finder, wordsE, "ile", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "yle", true); // pyle
      removeWordsWithSuffix(allNouns, finder, wordsE, "âle", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nle", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "êle", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "île", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "hle", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "zle", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fle", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "le", true);

      // me
      removeWordsWithSuffix(allNouns, finder, wordsE, "ume", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "dème", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "xème", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ième", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ème", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tome", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "angiome", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nome", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "iome", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ome", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "zyme", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "yme", true); // nyme
      removeWordsWithSuffix(allNouns, finder, wordsE, "sme", true); // isme, ïsme, ysme
      removeWordsWithSuffix(allNouns, finder, wordsE, "mme", true); // gramme
      removeWordsWithSuffix(allNouns, finder, wordsE, "ôme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ime", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "hme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fame", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "game", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ame", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "therme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "forme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "derme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rme", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ême", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "îme", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "âme", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "me", false);


      // ne
      removeWordsWithSuffix(allNouns, finder, wordsE, "cne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "dne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ine", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phène", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gène", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ène", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ane", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gone", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tone", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phone", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "one", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "une", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ïne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ône", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "âne", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mne", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "êne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lne", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ûne", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "sne", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "yne", true);

      // oe
      removeWordsWithSuffix(allNouns, finder, wordsE, "oe", true);

      // pe
      removeWordsWithSuffix(allNouns, finder, wordsE, "upe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ope", true); // trope scope
      removeWordsWithSuffix(allNouns, finder, wordsE, "ppe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ype", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ape", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mpe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ipe", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "carpe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rpe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "èpe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "pe", false);

      // re
      removeWordsWithSuffix(allNouns, finder, wordsE, "uvre", false); // pieuvre couleuvre
      removeWordsWithSuffix(allNouns, finder, wordsE, "vre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mère", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tère", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fère", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ère", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "cre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "bre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phare", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "are", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "dre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ore", true); // phore, chore, more
      removeWordsWithSuffix(allNouns, finder, wordsE, "toire", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "zoaire", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ire", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "saure", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "chlorure", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ure", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tre", true); // mètre
      removeWordsWithSuffix(allNouns, finder, wordsE, "yre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "üre", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "fre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rre", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "re", false);

      // se
      removeWordsWithSuffix(allNouns, finder, wordsE, "use", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ise", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "sse", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ose", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "yse", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "èse", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ase", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rse", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nse", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "pse", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ôse", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ïse", true);

      // te
      removeWordsWithSuffix(allNouns, finder, wordsE, "lte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "pte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "aste", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "auste", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uste", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ste", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ite", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phate", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "crate", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "late", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ate", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ote", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ute", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "cète", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ète", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "yte", true); // cyte, lyte
      removeWordsWithSuffix(allNouns, finder, wordsE, "rte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "xte", true); // texte
      removeWordsWithSuffix(allNouns, finder, wordsE, "lecte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ecte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ête", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ôte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ïte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "îte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ephte", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "phte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "mte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "cte", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "te", false);


      // ue
      removeWordsWithSuffix(allNouns, finder, wordsE, "oue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "logue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "gue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "pithèque", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lytique", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "isque", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "que", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "nue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "due", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "eue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "lue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "tue", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "cue", false);

      // ve
      removeWordsWithSuffix(allNouns, finder, wordsE, "ive", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ave", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uve", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ève", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "rve", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ve", false);

      // xe
      removeWordsWithSuffix(allNouns, finder, wordsE, "exe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "axe", false); // taxe
      removeWordsWithSuffix(allNouns, finder, wordsE, "ixe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "oxe", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "xe", true);

      // ye
      removeWordsWithSuffix(allNouns, finder, wordsE, "aye", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "eye", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ye", false);

      // ze
      removeWordsWithSuffix(allNouns, finder, wordsE, "aze", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ize", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "uze", false);
      removeWordsWithSuffix(allNouns, finder, wordsE, "èze", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "oze", true);
      removeWordsWithSuffix(allNouns, finder, wordsE, "ze", true);

      //
      // ,
      //
      //
      //

      computeGenders(allNouns, wordsE, wordsE, "e", false);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private static void removeWordsWithPrefix(Map<String, Noun> nouns, EndingFinder finder,
      List<String> words, String prefix, boolean expected) {
    List<String> wordsWithPrefix = finder.getWordsWithPrefix(nouns, prefix);
    computeGenders(nouns, words, wordsWithPrefix, prefix, expected);
    words.removeAll(wordsWithPrefix);
  }


  private static void removeWordsWithSuffix(Map<String, Noun> nouns, EndingFinder finder,
      List<String> words, String suffix, boolean expected) {
    List<String> wordsWithSuffix = finder.getWordsEndingIn(nouns, suffix);
    computeGenders(nouns, words, wordsWithSuffix, suffix, expected);
    words.removeAll(wordsWithSuffix);
  }

  private static void computeGenders(Map<String, Noun> nouns, List<String> remainingWords,
      List<String> wordsWithSuffix, String suffix, boolean expected) {
    int nbMasculine = 0, total = wordsWithSuffix.size();
    List<String> exceptions = new ArrayList<String>();

    for (String word : wordsWithSuffix) {
      Noun noun = nouns.get(word);
      boolean result = isMasculine(nouns, word);
      boolean exception = (result != expected);

      nbMasculine += noun.isMasculine() ? 1 : 0;

      if (remainingWords.contains(word)) {
        if (exception && exceptions.size() < 200) {
          exceptions.add(word);
        }
      }
    }

    Percent percent = Percent.of(nbMasculine, total);
    Collections.sort(exceptions);

    String msg = MessageFormat.format("{0} words in -{1} : {2} masculine, ({3})", total, suffix,
        nbMasculine, percent);
    System.out.println(msg);
    System.out.println("  " + exceptions);
  }

  private static boolean isMasculine(Map<String, Noun> nouns, String word) {
    Noun noun = nouns.get(word);
    return noun.isMasculine();
  }

  private static Map<String, Noun> loadDictionary() throws IOException {
    int nb = 96 * 1000;
    NounReader reader = NounReader.of(NounReader.class, "nouns.txt");
    Map<String, Noun> nouns = reader.read(nb);
    return nouns;
  }

  private static void printEndings(Map<String, List<String>> endings) {
    for (String ending : endings.keySet()) {
      int nb = endings.get(ending).size();
      String msg = MessageFormat.format("  -{0} : {1}", ending, nb);
      System.out.println(msg);
    }

  }

}
