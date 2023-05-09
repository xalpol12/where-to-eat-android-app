//package dev.xalpol12.wheretoeat.view.task;
//
//import android.graphics.PorterDuff;
//import android.os.AsyncTask;
//import android.view.View;
//
//import androidx.appcompat.widget.AppCompatButton;
//
//import java.util.List;
//
//public class ChangePriceButtonColorTask extends AsyncTask<Integer, Void, Void> {
//    private final List<AppCompatButton> buttons;
//    int selectedBtnIndex;
//    int primaryColor;
//    int accentColor;
//
//    public ChangePriceButtonColorTask(List<AppCompatButton> buttons,
//                               int selectedBtnIndex,
//                               int primaryColor,
//                               int accentColor) {
//        this.buttons = buttons;
//        this.selectedBtnIndex = selectedBtnIndex;
//        this.primaryColor = primaryColor;
//        this.accentColor = accentColor;
//    }
//
//    @Override
//    protected Void doInBackground(Integer... integers) {
//        for (int i = 0; i < selectedBtnIndex; i++) {
//            buttons.get(i).getCompoundDrawables()[0].setColorFilter(accentColor, PorterDuff.Mode.SRC_ATOP);
//        }
//        for (int i = selectedBtnIndex+1; i < buttons.size(); i++) {
//            buttons.get(i).getCompoundDrawables()[0].setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
//        }
//    }
//}
//
//
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        executor.execute(() -> {
//
//            handler.post(() -> {
//
//            });
//        });