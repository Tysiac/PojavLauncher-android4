package net.kdt.pojavlaunch.multirt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.webkit.MimeTypeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.kdt.pojavlaunch.BaseLauncherActivity;
import net.kdt.pojavlaunch.R;
import android.os.Build;

public class MultiRTConfigDialog {
    public static final int MULTIRT_PICK_RUNTIME = 2048;
    public static final int MULTIRT_PICK_RUNTIME_STARTUP = 2049;
    public AlertDialog dialog;
    public RecyclerView dialogView;
    public void prepare(BaseLauncherActivity ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(R.string.multirt_config_title);
        dialogView = new RecyclerView(ctx);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialogView.setLayoutManager(linearLayoutManager);
        dialogView.setAdapter(new RTRecyclerViewAdapter(this));
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.multirt_config_add, (dialog, which) -> openRuntimeSelector(ctx,MULTIRT_PICK_RUNTIME));
        builder.setNegativeButton(R.string.mcn_exit_call, (dialog, which) -> dialog.cancel());
        dialog = builder.create();
    }
    public void refresh() {
        RecyclerView.Adapter adapter = dialogView.getAdapter();
        if(adapter != null)dialogView.getAdapter().notifyDataSetChanged();
    }
   public static void openRuntimeSelector(Activity ctx, int code) {
    Intent intent;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
    } else {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
    }

    String mimeType = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension("xz");
    if (mimeType == null) mimeType = "*/*";

    intent.setType(mimeType);

    ctx.startActivityForResult(intent, code);
  }
}
