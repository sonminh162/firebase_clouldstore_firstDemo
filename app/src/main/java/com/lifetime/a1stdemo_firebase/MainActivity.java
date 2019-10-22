package com.lifetime.a1stdemo_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");

    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    return;
                }
                String data = "";

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Note note = documentSnapshot.toObject(Note.class);
                    note.setDocumentId(documentSnapshot.getId());

                    String documentId = note.getDocumentId();
                    String title = note.getTitle();
                    String description = note.getDescription();
                    int priority = note.getPriority();

                    data += "ID: "+documentId+"\nTitle: "+title+"\nDescription: "+description
                            +"\nPriority: "+priority
                            + "\n\n";
                }

                textViewData.setText(data);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPriority = findViewById(R.id.edit_text_priority);
        textViewData = findViewById(R.id.text_view_data);
    }

    public void addNote(View view) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if(editTextPriority.length() == 0 ){
            editTextPriority.setText("0");
        }

        int priority = Integer.parseInt(editTextPriority.getText().toString());

        Note note = new Note(title, description, priority);

        notebookRef.add(note);
    }


    public void loadNotes(View view) {
        Task task1 = notebookRef.whereLessThan("priority", 2)
                .orderBy("priority")
                .get();

        Task task2 = notebookRef.whereGreaterThan("priority",2)
                .orderBy("priority").limit(3)
                .get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1,task2);
        allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                String data = "";

                for(QuerySnapshot queryDocumentSnapshots : querySnapshots){
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        Note note = documentSnapshot.toObject(Note.class);
                        note.setDocumentId(documentSnapshot.getId());

                        String documentId = note.getDocumentId();
                        String title = note.getTitle();
                        String description = note.getDescription();
                        int priority = note.getPriority();

                        data += "ID: "+documentId
                                +"\nTitle: title" +"\nDescription: "+description
                                +"\nPriority: "+ priority + "\n\n";
                    }
                }

                textViewData.setText(data);
            }
        });
    }
}
