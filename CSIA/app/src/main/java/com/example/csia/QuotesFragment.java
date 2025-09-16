package com.example.csia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuotesFragment extends Fragment {
    private TextView quoteTextView, authorTextView;
    private Button nextQuoteButton;
    private List<Quote> quotes;
    private int currentQuoteIndex = -1;
    private Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        quoteTextView = view.findViewById(R.id.quote_text);
        authorTextView = view.findViewById(R.id.quote_author);
        nextQuoteButton = view.findViewById(R.id.next_quote_button);

        // Initialize quotes
        initializeQuotes();

        // Display random quote
        showRandomQuote();

        nextQuoteButton.setOnClickListener(v -> showRandomQuote());

        return view;
    }

    private void initializeQuotes() {
        quotes = new ArrayList<>();
        quotes.add(new Quote("The secret of getting ahead is getting started.", "Mark Twain"));
        quotes.add(new Quote("Your time is limited, so don't waste it living someone else's life.", "Steve Jobs"));
        quotes.add(new Quote("It does not matter how slowly you go as long as you do not stop.", "Confucius"));
        quotes.add(new Quote("Quality is not an act, it is a habit.", "Aristotle"));
        quotes.add(new Quote("We are what we repeatedly do. Excellence, then, is not an act, but a habit.", "Aristotle"));
        quotes.add(new Quote("The best way to predict your future is to create it.", "Abraham Lincoln"));
        quotes.add(new Quote("Success is the sum of small efforts, repeated day in and day out.", "Robert Collier"));
    }

    private void showRandomQuote() {
        int newIndex;
        do {
            newIndex = random.nextInt(quotes.size());
        } while (newIndex == currentQuoteIndex && quotes.size() > 1);

        currentQuoteIndex = newIndex;
        Quote quote = quotes.get(currentQuoteIndex);
        quoteTextView.setText(quote.getText());
        authorTextView.setText("- " + quote.getAuthor());
    }

    // Simple Quote class
    private static class Quote {
        private String text;
        private String author;

        public Quote(String text, String author) {
            this.text = text;
            this.author = author;
        }

        public String getText() { return text; }
        public String getAuthor() { return author; }
    }
}