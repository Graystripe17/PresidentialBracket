package com.example.gaga.presidentialbracket;

public class Comment {
    String _Candidate;
    String _PosterName;
    int _ReplyToId;
    String _Comment;
    int _Rating;
    int _ID;

    public Comment(String Candidate) {
        this._Candidate = Candidate;
    }
    public Comment(String Candidate, String PosterName, int ReplyToId, String Comment) {
        this._Candidate = Candidate;
        this._PosterName = PosterName;
        this._ReplyToId = ReplyToId;
        this._Comment = Comment;
    }
    public String get_Candidate() {
        return _Candidate;
    }
    public String get_Comment() {
        return this._Comment;
    }
    public String get_Name() {
        return this._PosterName;
    }
    public String toString() {
        return this._Candidate + this._PosterName + this._ID;
    }
}
