package com.marcosavard.commons.ling.fr.dic;

import com.marcosavard.commons.io.writer.FormatWriter;
import com.marcosavard.commons.util.collection.UniqueList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Glossary {

    public enum Category {
        GREECE,
        ROME,
        GAUL,
        MIDDLE_AGE,
        VIKING,
        CHRISTIAN;
    }

    private static final String[] GREECE_WORDS = new String[] {
            "minotaure", "méduse", "centaure", "gorgone", "sphinx", "chimère", "phénix", "pégase", "sphinx",
            "titan", "nymphe", "cyclope", "théogonie", "odyssée",
            "achéen", "troyen", "mycénien", "dorien", "dorique", "ionien", "ionique",
            "athénien", "spartiate", "attique", "corinthien",
            "olympien",  "olympe", "olympiade", "athlète", "pancrace", "pugilat", "pentathlon", "hippodrome",
            "hellène", "stade", "gymnase", "palestre",
            "temple", "parthénon", "stèle", "propylée", "théâtre", "odéon", "tragédie", "comédie", "satyre",
            "acropole", "agora", "aède",  "rhapsode", "philosophe", "rhéteur", "augure",
            "oracle", "hymne", "sophiste", "métèque",
            "hoplite", "phalange", "stratège", "hipparque", "trière",
            "démocratie", "oligarchie", "tyrannie", "duarchie",
            "obole", "drachme", "tétradrachme", "dodécadrachme", "rhyton", "fibule",  "pyxide",
            "lécythe", "hydrie", "triglyphe", "métope",
    };

    private static final String[] ROME_WORDS = new String[] {
            "République", "Sénat", "Consul", "Préteur", "Questeur", "Édile", "Censeur",
            "Dictateur", "Tribun", "Plébéien", "Patricien", "Légion", "Légionnaire", "Centurion",
            "Cohorte", " Manipule", "Légat", "Forum", "Curie", "Comices", "vestale",
            "Triumvirat", "Proconsul", "Propréteur", "Municipium", "Gladiateur", "Cirque", "Amphithéâtre",
            "Aqueduc", " Capitole", "Rostres", "Tabularium", "Palatin", "Aventin", "Esquilin",
            "Caelius", " Quirinal", "Viminal", "Janicule", "Latium", "Carthage", "Empereur",
            "César", "Auguste", "Princeps", "étrusque", "vandale", "italique", "limes",
            "celte", "toge", "calende", "none", "colisée", "pontife", "principat", "denier",
            "dace", "numide", "sesterce", "triumvir", "glaive", "ibère", "prétorien",
            "therme", "censure", "cisalpine", "sabin", "latin", "samnite", "tacite",
            "tétrarchie", "sénateur", "oppidum", "char", "", "", "",
            "", "", "", "", "", "", "",
    };

    private static final String[] GAULE_WORDS = new String[] {
            "Gaulois", "Celte", "celtique", "Druide", "druidique", "Torque", "Fibule", "barde",
            "cervoise", "braie", "éduen", "armoricain", "potion", "amphore", "saie", "serpe",
            "menhir", "dolmen", "", "", "", "",
            "", "", "", "", "", "", "",
    };

    private static final String[] MIDDLE_AGE_WORDS = new String[] {
            "Château", "Chevalier", "Féodalité", "Seigneur", "Serf", "vassal", "Suzerain",
            "Donjon", "Croisade", "Tournoi", "Joute", "Héraldique", "Blason", "Bannière",
            "Forteresse", "Rempart", "Muraille", "Pont-levis", "Douves", "Guilde", "",
            "Tisserand", "Parchemin", "Enluminure", "Scriptorium", "Manuscrit", "Scribe", "Paladin",
            "Troubadour", "Minstrel", "Trouvère", "Cuirasse", "Heaume", "Écu", "Arbalète",
            "Alchimie", "Grimoire", "Trébuchet", "Catapulte", "Fief", "Oubliette", "Gibet",
            "Potence", "Gabelle", "Ban", "Hydromel", "Tour", "Créneau", "Meurtrière", "Courtine",
            "Bastion", "Barbacane", "Herse", "Muraille", "Échauguette", "Châtelet", "Citadelle",
            "Carolingien", "Sceptre", "Archer", "Haubert", "Dague", "Martel", "Masse",
            "Fléau", "Coutelas", "Poignard", "Pique", "Pertuisane", "Hallebarde", "Fauchard",
            "Guisarme", "Vouge", "Bardiche", "Épieu", "Baliste", "Mangonneau", "Beffroi",
            "Palissade", "Estacade", "Redoute", "Targe", "Rondache", "Pavois", "Cotte",
            "Brigandine", "Gorgerin", "Cubitière", "Gantelet", "Soleret", "Éperon", "Caparaçon",
            "Bardage", "Chanfrein", "Gargouille", "Griffon", "Hippogriffe", "Ogre", "Bûcher",
            "Gibet", "", "", "", "", "", "",
    };

    private static final String[] VIKINGS_WORDS = new String[] {
            "Viking", "Normand", "Drakkar", "Odin", "Thor", "Valkyrie",
            "Saga", "Troll", "Kraken", "Elfe", "Golem", "Draugr", "Valhalla",
            "Rune", "runique", "norrois", "varègues", "", "", "",
            "", "", "", "", "", "", "",
    };

    private static final String[] CHRISTIAN_WORDS = new String[] {
            "Monastère", "Moine", "Abbaye", "Relique", "Reliquaire", "Pèlerin", "Pèlerinage",
            "Calice", "Ciboire", "Ostensoir", "Cloître", "Réfectoire", "Chapelle", "Oratoire",
            "Clocher", "Croix", "Crucifix", "Pape", "Cardinal", "Évêque",
            "Archevêque", "Prêtre", "curé", "Diacre", "Sacrement", "Baptême", "Eucharistie",
            "Confession", "Mariage", "Ordination", "Extrême-onction", "Pénitence", "Purgatoire",
            "Enfer", "Paradis", "Ange", "Démon", "Satan", "Diable", "Exorcisme",
            "Inquisition", "Hérésie", "Bible", "évangile", "Grâce", "Miséricorde", "Charité",
            "Aumône", "carême", "Prière", "Foi", "Religion", "Chrétien", "Païen",
            "Messe", "confessionnal", "sermon", "résurrection", "Tabernacle", "Hostie", "Communion",
            "Église", "Cathédrale", "Basilique", "Autel", "Nonne", "Couvent", "Chœur",
            "Nef", "Transept", "Abside", "Crypte", "Saint", "Sainte", "Martyr",
            "Ange", "Archange", "Chérubin", "Séraphin", "Péché", "Rédemption", "Onction",
            "Credo", "Psaume", "Prophète", "Apôtre", "Disciple", "Jésus", "Christ", "Messie",
            "Saint-Esprit", "Pentecôte", "Noël", "Épiphanie", "Pâques", "Avent", "Toussaint",
            "Assomption", "Rosaire", "Chapelet", "Vitrail", "Cierge", "Encens", "Bénitier",
            "Chasuble", "Étole", "Ostensoir", "Custode", "Lunule", "Patène", "Corporal",
            "Liturgie", "Encyclique", "Canonisation", "Béatification", "Théologie", "Miracle", "Homélie",
            "Catéchèse", "Catéchisme", "Catéchumène", "Séminaire", "Séminariste", "Évangélisation", "Apostolat",
            "", "", "", "", "", "", "",
    };


    private List<Category> categories = new ArrayList<>();
    private DicoDefinitionReader reader = new DicoDefinitionReader();
    private static List<Word> allWords = null;
    private static List<FormatWriter> listeners = new ArrayList<>();

    public void addListener(FormatWriter listener) {
        listeners.add(listener);
    }


    public void addCategory(Category category) {
        categories.add(category);
    }

    public List<Word> getWordList() throws IOException {
        List<Word> wordList = new UniqueList<>();

        if (allWords == null) {
            writeLine("..reading");
            allWords = reader.readAll();
            writeLine("  {0} words read", allWords.size());
        }

        if (categories.contains(Category.GREECE)) {
            wordList.addAll(findGreekGlossary(allWords));
        }

        if (categories.contains(Category.ROME)) {
            wordList.addAll(findRomanGlossary(allWords));
        }

        if (categories.contains(Category.GAUL)) {
            wordList.addAll(findGaulGlossary(allWords));
        }

        if (categories.contains(Category.MIDDLE_AGE)) {
            wordList.addAll(findMiddleAgeGlossary(allWords));
        }

        if (categories.contains(Category.VIKING)) {
            wordList.addAll(findVikingsGlossary(allWords));
        }

        if (categories.contains(Category.CHRISTIAN)) {
            wordList.addAll(findChristianGlossary(allWords));
        }

        wordList.removeAll(wordList.stream().filter(w -> w.hasDefinitionStartingWith("commune")).toList());
        wordList.removeAll(wordList.stream().filter(w -> w.hasDefinitionStartingWith("première personne")).toList());
        wordList.removeAll(wordList.stream().filter(w -> w.hasDefinitionStartingWith("deuxième personne")).toList());
        wordList.removeAll(wordList.stream().filter(w -> w.hasDefinitionStartingWith("troisième personne")).toList());
        wordList.removeAll(wordList.stream().filter(w -> w.hasDefinitionStartingWith("participe présent")).toList());
        wordList.removeAll(wordList.stream().filter(w -> w.hasDefinitionStartingWith("participe passé")).toList());

        wordList = wordList.stream().sorted(Comparator.comparing(w -> w.getText())).toList();

        return wordList;
    }

    private void writeLine() {
        writeLine("");
    }

    private void writeLine(String line) {
        for (PrintWriter writer : listeners) {
            writer.println(line);
            writer.flush();
        }
    }

    private void writeLine(String pattern, Object... params) {
        for (FormatWriter writer : listeners) {
            writer.println(pattern, params);
            writer.flush();
        }
    }


    private List<Word> findGreekGlossary(List<Word> allWords) {
        List<Word> glossary = new ArrayList<>();
        glossary.addAll(allWords.stream()
                .filter(w -> w.hasDefinitionStartingWith("Antiquité grecque") || //
                        w.hasDefinitionStartingWith("Antiquité") && w.containsAny("athènes", "grec", "olympique"))
                .toList());

        for (String lexicon : GREECE_WORDS) {
            List<Word> wordList = allWords.stream().filter(w -> w.getText().equalsIgnoreCase(lexicon)).toList();

            if (! wordList.isEmpty()) {
                glossary.addAll(wordList);
            }
        }

        cleanUpDefinitions(glossary, "Antiquité grecque", "Antiquité");
        return glossary;
    }

    private Collection<? extends Word> findRomanGlossary(List<Word> allWords) {
        List<Word> glossary = new UniqueList<>();
        glossary.addAll(allWords.stream()
                .filter(w -> w.hasDefinitionStartingWith("Histoire romaine")
                        || w.hasDefinitionStartingWith("Antiquité") && ! w.hasDefinitionStartingWith("Antiquité grecque"))
                .toList());

        for (String lexicon : ROME_WORDS) {
            List<Word> wordList = allWords.stream().filter(w -> w.getText().equalsIgnoreCase(lexicon)).toList();

            if (! wordList.isEmpty()) {
                glossary.addAll(wordList);
            }
        }

        cleanUpDefinitions(glossary, "Antiquité romaine", "Antiquité", "antiquité");
        return glossary;
    }


    private Collection<? extends Word> findGaulGlossary(List<Word> allWords) {
        List<Word> glossary = new UniqueList<>();
        for (String lexicon : GAULE_WORDS) {
            List<Word> wordList = allWords.stream().filter(w -> w.getText().equalsIgnoreCase(lexicon)).toList();

            if (! wordList.isEmpty()) {
                glossary.addAll(wordList);
            }
        }
        return glossary;

    }

    private Collection<? extends Word> findMiddleAgeGlossary(List<Word> allWords) {
        List<Word> glossary = new UniqueList<>();
        glossary.addAll(allWords.stream()
                .filter(w -> w.containsAny("carolingien", "féodal", "médiévale", "mérovingien"))
                .toList());

        for (String lexicon : MIDDLE_AGE_WORDS) {
            List<Word> wordList = allWords.stream().filter(w -> w.getText().equalsIgnoreCase(lexicon)).toList();

            if (! wordList.isEmpty()) {
                 glossary.addAll(wordList);
            }
        }

        cleanUpDefinitions(glossary, "Droit féodal", "Féodalité");
        return glossary;
    }

    private static Collection<? extends Word> findVikingsGlossary(List<Word> allWords) {
        List<Word> glossary = new UniqueList<>();
        glossary.addAll(allWords.stream()
                .filter(w -> w.containsAny("viking"))
                .toList());

        for (String lexicon : VIKINGS_WORDS) {
            List<Word> wordList = allWords.stream().filter(w -> w.getText().equalsIgnoreCase(lexicon)).toList();

            if (! wordList.isEmpty()) {
                glossary.addAll(wordList);
            }
        }

        return glossary;
    }

    private static Collection<? extends Word> findChristianGlossary(List<Word> allWords) {
        List<Word> glossary = new UniqueList<>();
        glossary.addAll(allWords.stream()
                .filter(w -> w.containsAny("lithurgie", "religion"))
                .toList());

        for (String lexicon : CHRISTIAN_WORDS) {
            List<Word> wordList = allWords.stream().filter(w -> w.getText().equalsIgnoreCase(lexicon)).toList();

            if (! wordList.isEmpty()) {
                glossary.addAll(wordList);
            }
        }

        return glossary;
    }

    private void cleanUpDefinitions(List<Word> glossary, String... categories) {
        for (Word word : glossary) {
            for (int i=0; i<word.getDefinitions().size(); i++) {
                cleanUpDefinition(word, i, categories);
            }
        }
    }

    private void cleanUpDefinition(Word word, int idx, String[] categories) {
        if (word.getText().startsWith("Quad")) {
            writeLine();
        }

        for (String category : categories) {
            String def = word.getDefinitions().get(idx).replace(category, "").trim();
            word.getDefinitions().set(idx, def);
        }
    }


}
