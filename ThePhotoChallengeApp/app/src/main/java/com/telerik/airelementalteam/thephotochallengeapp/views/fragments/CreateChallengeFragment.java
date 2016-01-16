package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Trace;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.CreateChallengePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateChallengeFragment extends Fragment{

    private CreateChallengePresenter presenter;
    private TextView titleText;
    private TextView themeText;
    private RadioGroup publicRadioGroup;
    private ImageView datepickerButton;
    private TextView datepickText;
    private PopupWindow dropdownWindow;
    private AppCompatButton createChallengeButton;

    public CreateChallengeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new CreateChallengePresenter(this.getActivity());
        final View view = inflater.inflate(R.layout.fragment_create_challenge, container, false);
        datepickerButton = (ImageView) view.findViewById(R.id.datepicker_button);
        datepickText = (TextView) view.findViewById(R.id.pickdate_text);
        createChallengeButton = (AppCompatButton) view.findViewById(R.id.create_challenge_button);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                datepickText.setText(sdf.format(calendar.getTime()));
                //updateLabel();
            }
        };

        datepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK MADAFAKA");
                new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK MADAFAKA");
            }
        });

        return view;
    }
}
