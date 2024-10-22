package com.example.moverfotos;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Encuentra el GridLayout
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Asigna un OnTouchListener a cada ImageView para iniciar el arrastre
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);

        imageView1.setOnTouchListener(new MyTouchListener());
        imageView2.setOnTouchListener(new MyTouchListener());

        // Asigna un DragListener al GridLayout para recibir los eventos de arrastre
        gridLayout.setOnDragListener(new MyDragListener());
    }

    // Clase para manejar el toque que inicia el arrastre
    private class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                // Crea un ClipData vacío solo para iniciar el arrastre
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    // Clase para manejar los eventos de arrastre y soltar
    private class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // No se hace nada al iniciar el arrastre
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // Se puede cambiar el color o la apariencia del GridLayout cuando se arrastra algo dentro
                    v.setBackgroundColor(0x30000000); // Ejemplo de cambio de color
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(0); // Restaura el color original
                    return true;
                case DragEvent.ACTION_DROP:
                    // Aquí puedes mover la imagen al nuevo lugar en el GridLayout
                    View draggedView = (View) event.getLocalState();
                    GridLayout parent = (GridLayout) v;
                    parent.removeView(draggedView); // Elimina la vista original
                    parent.addView(draggedView);    // Añade la vista en la nueva posición
                    draggedView.setVisibility(View.VISIBLE);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (!event.getResult()) {
                        // Si el arrastre no tuvo éxito, haz que la vista vuelva a ser visible
                        View draggedView2 = (View) event.getLocalState();
                        draggedView2.setVisibility(View.VISIBLE);
                    }
                    v.setBackgroundColor(0); // Restaura el color original
                    return true;
                default:
                    break;
            }
            return true;
        }
    }
}
