package com.example.authenticatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView dataListView;
    String[] name = {"Bulbasaur","Charmander","Squirtle","Pikachu"};
    String[] disc = {"In the Pokémon franchise, Bulbasaur are small,\nsquat amphibian and plant Pokémon that move on all four legs,\nand have light blue-green bodies with darker blue-green spots.\nAs a Bulbasaur undergoes evolution into Ivysaur and then later into Venusaur,\nthe bulb on its back blossoms into a large flower.",
            "Charmander is a Fire-type Lizard Pokémon.\nIt is the Fire-type Starter Pokémon in the Generation\nI Pokémon games and their remakes.\nAt level 16, Charmander evolves into a Charmeleon.\nIts final evolution is Charizard which it evolves into at Level 36.",
            "Squirtle is a Water-type Pokémon introduced in Generation I.\nIt evolves into Wartortle starting at level 16,\nwhich evolves into Blastoise starting at level 36.",
            "Pikachu are small, mouse-like Pokémon that have short,\nyellow fur with brown markings covering their backs and parts of their tails.\nThey have black-tipped,\npointy ears and red circles on their cheeks,\nwhich are said to contain \"electrical sacs\".\nTheir tails are shaped in the form of a lightning bolt."};
    Integer[] img = {R.drawable.bulbasaur,R.drawable.charmander,R.drawable.squirtle,R.drawable.pikachu};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder detailDialogBox = new AlertDialog.Builder(MainActivity.this);
        detailDialogBox.setMessage("Alert Dialog Box");

        dataListView = (ListView) findViewById(R.id.dataListView);

        CustomListView customListView = new CustomListView(this,name,img);
        dataListView.setAdapter(customListView);

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                detailDialogBox.setIcon(img[i]);
                detailDialogBox.setTitle(name[i]);
                detailDialogBox.setMessage(disc[i]);
                detailDialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {

                    }
                });
                detailDialogBox.create().show();

                Toast.makeText(getApplicationContext(),name[i],Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout (View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}