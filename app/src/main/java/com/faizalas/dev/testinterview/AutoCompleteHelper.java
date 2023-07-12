package com.faizalas.dev.testinterview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteHelper {

    private static final String BASE_URL = "https://insw-dev.ilcs.co.id/n/";
    private static final String NEGARA_URL = BASE_URL + "negara?ur_negara=";
    private static final String PELABUHAN_URL = BASE_URL + "pelabuhan?kd_negara=%s&ur_pelabuhan=";
    private static final String BARANG_URL = BASE_URL + "barang?hs_code=";
    private static final String TARIF_URL = BASE_URL + "tarif?hs_code=";

    public static void setupAutoCompleteNegara(final Context context, final AutoCompleteTextView autoCompleteTextView) {
        final RequestQueue queue = Volley.newRequestQueue(context);

        autoCompleteTextView.setAdapter(null);
        autoCompleteTextView.addTextChangedListener(null);

        autoCompleteTextView.addTextChangedListener(new DelayedTextWatcher(3, new DelayedTextWatcher.TextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                try {
                    String encodedText = URLEncoder.encode(text, "UTF-8");
                    String url = NEGARA_URL + encodedText;

                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        List<String> suggestions = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            suggestions.add(jsonArray.getString(i));
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                                                android.R.layout.simple_dropdown_item_1line, suggestions);
                                        autoCompleteTextView.setAdapter(adapter);
                                        autoCompleteTextView.showDropDown();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("AutoCompleteHelper", "Error: " + error.toString());
                                }
                            });

                    queue.add(request);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public static void setupAutoCompletePelabuhan(final Context context, final AutoCompleteTextView autoCompleteTextView, final String kdNegara) {
        final RequestQueue queue = Volley.newRequestQueue(context);

        autoCompleteTextView.setAdapter(null);
        autoCompleteTextView.addTextChangedListener(null);

        autoCompleteTextView.addTextChangedListener(new DelayedTextWatcher(3, new DelayedTextWatcher.TextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                try {
                    String encodedText = URLEncoder.encode(text, "UTF-8");
                    String url = String.format(PELABUHAN_URL, kdNegara) + encodedText;

                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        List<String> suggestions = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            suggestions.add(jsonArray.getString(i));
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                                                android.R.layout.simple_dropdown_item_1line, suggestions);
                                        autoCompleteTextView.setAdapter(adapter);
                                        autoCompleteTextView.showDropDown();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("AutoCompleteHelper", "Error: " + error.toString());
                                }
                            });

                    queue.add(request);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public static void setupAutoCompleteBarang(final Context context, final AutoCompleteTextView autoCompleteTextView) {
        final RequestQueue queue = Volley.newRequestQueue(context);

        autoCompleteTextView.setAdapter(null);
        autoCompleteTextView.addTextChangedListener(null);

        autoCompleteTextView.addTextChangedListener(new DelayedTextWatcher(3, new DelayedTextWatcher.TextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                try {
                    String encodedText = URLEncoder.encode(text, "UTF-8");
                    String url = BARANG_URL + encodedText;

                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    autoCompleteTextView.dismissDropDown();
                                    // Handle response
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("AutoCompleteHelper", "Error: " + error.toString());
                                }
                            });

                    queue.add(request);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public static void setupAutoCompleteTarif(final Context context, final AutoCompleteTextView autoCompleteTextView) {
        final RequestQueue queue = Volley.newRequestQueue(context);

        autoCompleteTextView.setAdapter(null);
        autoCompleteTextView.addTextChangedListener(null);

        autoCompleteTextView.addTextChangedListener(new DelayedTextWatcher(3, new DelayedTextWatcher.TextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                try {
                    String encodedText = URLEncoder.encode(text, "UTF-8");
                    String url = TARIF_URL + encodedText;

                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    autoCompleteTextView.dismissDropDown();
                                    // Handle response
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("AutoCompleteHelper", "Error: " + error.toString());
                                }
                            });

                    queue.add(request);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
