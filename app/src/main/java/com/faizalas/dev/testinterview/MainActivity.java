package com.faizalas.dev.testinterview;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView actv_negara, actv_pelabuhan, actv_barang;

    String urlNegara = "https://insw-dev.ilcs.co.id/n/negara?ur_negara";
    String urlPelabuhan = "https://insw-dev.ilcs.co.id/n/pelabuhan";
    String urlBarang = "https://insw-dev.ilcs.co.id/n/barang?hs_code=02044200";

    ArrayList<String> urlNamaNegara = new ArrayList<>();
    ArrayList<String> urlNamaPelabuhan = new ArrayList<>();
    ArrayList<String> urlNamaBarang = new ArrayList<>();

    ArrayList<String> urlIDNegara = new ArrayList<>();
    ArrayList<String> urlIDPelabuhan = new ArrayList<>();
    ArrayList<String> urlIDBarang = new ArrayList<>();

    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        progressDialog = new ProgressDialog(context);
        actv_negara = findViewById(R.id.actv_negara);
        actv_pelabuhan = findViewById(R.id.actv_pelabuhan);
        actv_barang = findViewById(R.id.actv_barang);

        setupAutoCompleteTextViews();
        showData();
    }

    private void setupAutoCompleteTextViews() {
        actv_negara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 3) {
                    autoSuggestNegara(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        actv_pelabuhan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String selectedNegara = actv_negara.getText().toString().trim();
                if (selectedNegara.length() == 2 && charSequence.length() == 3) {
                    autoSuggestPelabuhan(selectedNegara, charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        actv_barang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    autoSuggestBarang(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void autoSuggestNegara(String query) {
        String url = urlNegara + "=" + query;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            List<String> suggestedNegaras = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String ur_negara = object.getString("ur_negara");
                                suggestedNegaras.add(ur_negara);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, suggestedNegaras);
                            actv_negara.setAdapter(adapter);
                            actv_negara.showDropDown();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void autoSuggestPelabuhan(String kd_negara, String query) {
        String url = urlPelabuhan + "?kd_negara=" + kd_negara + "&ur_pelabuhan=" + query;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            List<String> suggestedPelabuhans = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String ur_pelabuhan = object.getString("ur_pelabuhan");
                                suggestedPelabuhans.add(ur_pelabuhan);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, suggestedPelabuhans);
                            actv_pelabuhan.setAdapter(adapter);
                            actv_pelabuhan.showDropDown();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void autoSuggestBarang(String query) {
        String url = urlBarang + "?hs_code=" + query;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            List<String> suggestedBarangs = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String ur_barang = object.getString("ur_barang");
                                suggestedBarangs.add(ur_barang);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, suggestedBarangs);
                            actv_barang.setAdapter(adapter);
                            actv_barang.showDropDown();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void showData() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                urlNegara,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String kd_negara = object.getString("kd_negara");
                                String ur_negara = object.getString("ur_negara");

                                urlIDNegara.add(kd_negara);
                                urlNamaNegara.add(ur_negara);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, urlNamaNegara);
                            actv_negara.setAdapter(arrayAdapter);

                            progressDialog.dismiss();
                            actv_negara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    urlIDPelabuhan.clear();
                                    urlNamaPelabuhan.clear();
                                    showDataPelabuhan(urlIDNegara.get(i));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void showDataPelabuhan(String idNegara) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = urlPelabuhan + "?kd_negara=" + idNegara;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String kd_pelabuhan = object.getString("kd_pelabuhan");
                                String ur_pelabuhan = object.getString("ur_pelabuhan");

                                urlIDPelabuhan.add(kd_pelabuhan);
                                urlNamaPelabuhan.add(ur_pelabuhan);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, urlNamaPelabuhan);
                            actv_pelabuhan.setAdapter(arrayAdapter);

                            progressDialog.dismiss();
                            actv_pelabuhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    urlIDBarang.clear();
                                    urlNamaBarang.clear();
                                    showDataBarang(urlIDPelabuhan.get(i));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void showDataBarang(String idPelabuhan) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = urlBarang + "&kd_pelabuhan=" + idPelabuhan;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String kd_barang = object.getString("kd_barang");
                                String ur_barang = object.getString("ur_barang");

                                urlIDBarang.add(kd_barang);
                                urlNamaBarang.add(ur_barang);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, urlNamaBarang);
                            actv_barang.setAdapter(arrayAdapter);

                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
