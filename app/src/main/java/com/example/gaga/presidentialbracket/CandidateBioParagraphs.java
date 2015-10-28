package com.example.gaga.presidentialbracket;


import com.jjoe64.graphview.series.DataPoint;

public class CandidateBioParagraphs {
    String name;
    String[] Description;
    String[] Strategy;
    String[] EconomyPlans;
    String[] SocialIssues;
    String[] ForeignPolicy;
    String[] Faults;
    String[] Record;

    CandidateBioParagraphs (String name, Bios biosContext) {
        this.name = name;
        String cleanName = name.replaceAll("'", "");
        this.Description = getStringPropertiesByName(cleanName, "Description", biosContext);
        this.Strategy = getStringPropertiesByName(cleanName, "Strategy", biosContext);
        this.EconomyPlans = getStringPropertiesByName(cleanName, "EconomyPlans", biosContext);
        this.SocialIssues = getStringPropertiesByName(cleanName, "SocialIssues", biosContext);
        this.ForeignPolicy = getStringPropertiesByName(cleanName, "ForeignPolicy", biosContext);
        this.Faults = getStringPropertiesByName(cleanName, "Faults", biosContext);
        this.Record = getStringPropertiesByName(cleanName, "Record", biosContext);
    }

    public static String[] getStringPropertiesByName(String cname, String Property, Bios context) {
        int resId = context.getResources().getIdentifier(cname + Property, "array", context.getPackageName());
        String[] allParagraphs = context.getResources().getStringArray(resId);
        return allParagraphs;
    }

}
