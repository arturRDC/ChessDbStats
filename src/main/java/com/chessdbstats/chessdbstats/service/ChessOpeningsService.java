package com.chessdbstats.chessdbstats.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChessOpeningsService {
    private static final HashMap<Pattern, String> openingMap = new HashMap<>();

    static {
        openingMap.put(Pattern.compile("A(0[0-9]|[1-9][0-9])"), "Flank openings");
        openingMap.put(Pattern.compile("A01"), "Nimzowitsch-Larsen Attack");
        openingMap.put(Pattern.compile("A0[23]"), "Bird's Opening");
        openingMap.put(Pattern.compile("A0[4-9]"), "Reti Opening");
        openingMap.put(Pattern.compile("A[1-3][0-9]"), "English opening");
        openingMap.put(Pattern.compile("A4[0-2]"), "Queen's Pawn Opening");
        openingMap.put(Pattern.compile("A43"), "Benoni/Old Benoni");
        openingMap.put(Pattern.compile("A4[5-9]"), "Queen's Pawn 1.d4 Nf6");
        openingMap.put(Pattern.compile("A5[1-2]"), "Budapest Gambit");
        openingMap.put(Pattern.compile("A5[3-5]"), "Old Indian Defence");
        openingMap.put(Pattern.compile("A56"), "Czech Benoni");
        openingMap.put(Pattern.compile("A5[7-9]"), "Benko/Volga Gambit");
        openingMap.put(Pattern.compile("A6[0-9]"), "Modern Benoni");
        openingMap.put(Pattern.compile("A8[0-9]"), "Dutch Defence");
        openingMap.put(Pattern.compile("B[0-9][0-9]"), "Semi-open games");
        openingMap.put(Pattern.compile("B00"), "King's Pawn Opening");
        openingMap.put(Pattern.compile("B01"), "Scandinavian Defence");
        openingMap.put(Pattern.compile("B0[2-5]"), "Alekhine Defence");
        openingMap.put(Pattern.compile("B06"), "Modern/Robatsch Defence");
        openingMap.put(Pattern.compile("B0[7-9]"), "Pirc Defence");
        openingMap.put(Pattern.compile("B1[0-9]"), "Caro-Kann Defence");
        openingMap.put(Pattern.compile("B[2-9][0-9]"), "Sicilian Defence");
        openingMap.put(Pattern.compile("C[0-9][0-9]"), "Open games");
        openingMap.put(Pattern.compile("C00"), "French Defence");
        openingMap.put(Pattern.compile("C20"), "King's Pawn Game");
        openingMap.put(Pattern.compile("C21"), "Danish Gambit");
        openingMap.put(Pattern.compile("C22"), "Center Game");
        openingMap.put(Pattern.compile("C2[3-4]"), "Bishop's Opening");
        openingMap.put(Pattern.compile("C2[5-9]"), "Vienna Game");
        openingMap.put(Pattern.compile("C3[0-2]"), "King's Gambit Declined");
        openingMap.put(Pattern.compile("C3[3-9]"), "King's Gambit Accepted");
        openingMap.put(Pattern.compile("C40"), "Latvian Gambit");
        openingMap.put(Pattern.compile("C41"), "Philidor Defence");
        openingMap.put(Pattern.compile("C4[2-3]"), "Petroff Defence, 3.Nxe5");
        openingMap.put(Pattern.compile("C44"), "Ponziani Opening");
        openingMap.put(Pattern.compile("C45"), "Scotch Game");
        openingMap.put(Pattern.compile("C46"), "Three Knights' Game");
        openingMap.put(Pattern.compile("C4[7-9]"), "Four Knights' Game");
        openingMap.put(Pattern.compile("C50"), "Italian Game - Giuoco Pianissimo");
        openingMap.put(Pattern.compile("C5[1-2]"), "Evans' Gambit");
        openingMap.put(Pattern.compile("C5[3-4]"), "Giuoco Piano, 4.c3");
        openingMap.put(Pattern.compile("C5[5-9]"), "Two Knights' Defence");
        openingMap.put(Pattern.compile("C[6-9][0-9]"), "Ruy Lopez");
        openingMap.put(Pattern.compile("D[0-9][0-9]"), "Closed and semi-closed games");
        openingMap.put(Pattern.compile("D00"), "Queen's Pawn Game");
        openingMap.put(Pattern.compile("D0[4-5]"), "Colle System");
        openingMap.put(Pattern.compile("D0[6-9]"), "Queen's Gambit, Irregular");
        openingMap.put(Pattern.compile("D1[0-9]"), "Slav Defence");
        openingMap.put(Pattern.compile("D2[0-9]"), "Queen's Gambit Accepted");
        openingMap.put(Pattern.compile("D30"), "Queen's Gambit Declined: Slav");
        openingMap.put(Pattern.compile("D31"), "Queen's Gambit Declined: Semi-Slav");
        openingMap.put(Pattern.compile("D3[2-4]"), "Queen's Gambit Declined: Tarrasch Defence");
        openingMap.put(Pattern.compile("D3[5-9]"), "Queen's Gambit Declined");
        openingMap.put(Pattern.compile("D4[3-9]"), "Queen's Gambit Declined: Semi-Slav main line");
        openingMap.put(Pattern.compile("D[5-6][0-9]"), "Queen's Gambit Declined: Classical");
        openingMap.put(Pattern.compile("D7[0-9]"), "Grünfeld Defence");
        openingMap.put(Pattern.compile("E[0-9][0-9]"), "Indian defences");
        openingMap.put(Pattern.compile("E00"), "Indian Defences");
        openingMap.put(Pattern.compile("E0[1-9]"), "Catalan Opening");
        openingMap.put(Pattern.compile("E10"), "Blumenfeld Gambit");
        openingMap.put(Pattern.compile("E11"), "Bogo-Indian Defence");
        openingMap.put(Pattern.compile("E1[2-9]"), "Queen's Indian Defence");
        openingMap.put(Pattern.compile("E[2-5][0-9]"), "Nimzo-Indian Defence");
        openingMap.put(Pattern.compile("E[6-9][0-9]"), "King's Indian Defence");
    }

    public static String getOpeningName(String code) {
        for (Map.Entry<Pattern, String> entry : openingMap.entrySet()) {
            if (isMatchingPattern(entry.getKey(), code)) {
                return entry.getValue();
            }
        }
        return "Unknown Opening";
    }

    private static boolean isMatchingPattern(Pattern pattern, String code) {
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

}
