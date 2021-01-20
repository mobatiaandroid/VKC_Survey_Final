package com.vkcrestore.SurveyTypes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vkcrestore.LoopQuestion;
import com.vkcrestore.R;
import com.vkcrestore.SearchUserActivity;
import com.vkcrestore.SurveyUpdateActivity;
import com.vkcrestore.ViewUserActivity;
import com.vkcrestore.Survey.Survey;
import com.vkcrestore.Survey.SurveyAnswers;
import com.vkcrestore.Survey.SurveyQuestions;
import com.vkcrestore.UsrValues.UserValues;
import com.vkcrestore.constants.GlobalConstants;
import com.vkcrestore.manager.AudioRecorder;
import com.vkcrestore.manager.Headermanager;
import com.vkcrestore.manager.QuestionFlowManager;
import com.vkcrestore.manager.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntInput extends Activity {

	private ImageView nextButton;
	private ImageView backButton;
	private TextView questionText;
	private SurveyAnswers s_answer;
	private SurveyAnswers ts_answer;
	private SurveyQuestions s_question;
	private int q_id;
	private EditText inputText;
	private int surveyAnswer_attend_pos;
	Headermanager headermanager;
	LinearLayout header;
	ImageView splitIcon, tickImg;
	Activity activity=this;
	Context context=this;
	Typeface typeface;
	TextView qDecisionTextView, txtqDecisionBack, txtqDecisionNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		typeface=Utils.setFontTypeToArial(context);
		setContentView(R.layout.q_intinput);
		header = (LinearLayout) findViewById(R.id.header);
		headermanager = new Headermanager(activity, "");
		headermanager.getHeader(header, 1, false);
		headermanager.setButtonLeftSelector(R.drawable.back, R.drawable.back);
		splitIcon = headermanager.getLeftButton();
		splitIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				IntInput.this.finish();

			}
		});
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			q_id = extras.getInt("qid");
			Survey.q_id = q_id;
		}

		nextButton = (ImageView) findViewById(R.id.bt_intip_next);
		backButton = (ImageView) findViewById(R.id.bt_intip_back);
		questionText = (TextView) findViewById(R.id.txt_intip_question);
		txtqDecisionBack = (TextView) findViewById(R.id.txtqDecisionBack);
		txtqDecisionNext = (TextView) findViewById(R.id.txtqDecisionNext);
		questionText.setTypeface(typeface);
		txtqDecisionBack.setTypeface(typeface);
		txtqDecisionNext.setTypeface(typeface);
		s_question = Survey.surveyQuestions.get(Survey.q_id);
		questionText.setText(s_question.getQuestionText());
		inputText = (EditText) findViewById(R.id.txt_intip_input);
		inputText.setTypeface(typeface);
		surveyAnswer_attend_pos = QuestionFlowManager.getposQuestioninAnswerArray(s_question.getQuestionId()) ;
		if(surveyAnswer_attend_pos!=-1){
		//if (Survey.surveyAnswers.size() > Survey.q_id) {
			ts_answer = Survey.surveyAnswers.get(surveyAnswer_attend_pos);
			inputText.setText(ts_answer.getSurveyAnswer());
		}

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!inputText.getText().toString().equals("")) {
					if(surveyAnswer_attend_pos!=-1){
					//if (Survey.surveyAnswers.size() > Survey.q_id) {
						Survey.surveyAnswers.get(surveyAnswer_attend_pos).setSurveyAnswer(
								"" + inputText.getText());
					} else {
						Timestamp currentTimeString = new Timestamp(new Date().getTime());
						s_answer = new SurveyAnswers(
								s_question.getQuestionId(), s_question
										.getQuestionText(), ""
										+ inputText.getText(),
								currentTimeString.toString(), 0,
								s_question.getQuestType(), null, null, null, null,Survey.q_id);
						Survey.surveyAnswers.add(s_answer);
					}

					//Survey.q_id++;
					
	/////////////////NEXT QUESTION //////////////
					LoopQuestion loopQuestion  = new LoopQuestion(IntInput.this);	
					
						Survey.q_id = loopQuestion.findNextQuestionidPositionFromSelectedQuestionid(s_question.getQuestionId());
						//System.out.println("Survey Ques Id Ininput " +Survey.q_id );
						//System.out.println("s_question.getQuestionId Ininput " +s_question.getQuestionId());
					loopQuestion.closeDB();
					
					/////////////////NEXT QUESTION //////////////
					
					if (Survey.q_id !=-1){
					//if (Survey.surveyQuestions.size() > Survey.q_id) {
						SurveyTypeUtils swit = new SurveyTypeUtils();
						startActivityForResult(
								swit.getIntent(getApplicationContext()),
								UserValues.VIEW_USER_REQ);
					} else {
						Intent i = new Intent(getApplicationContext(),
								SurveyUpdateActivity.class);
						startActivityForResult(i, UserValues.VIEW_USER_REQ);
					}
				} else {
					alertbox("Error", "Enter a number!!");
					inputText.setFocusable(true);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Survey.q_id = q_id;
		surveyAnswer_attend_pos = QuestionFlowManager.getposQuestioninAnswerArray(s_question.getQuestionId()) ;
		super.onResume();
		AudioRecorder.stopTimer();
	}
	@Override
	protected void onPause() 
	{
		super.onPause();
		AudioRecorder.setupLongTimeout(GlobalConstants.AUDIO_TIMEOUT, IntInput.this);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Survey.q_id = q_id;
		super.onStart();
	}

	protected void alertbox(String title, String mymessage) {
		new AlertDialog.Builder(this)
				.setMessage(mymessage)
				.setTitle(title)
				.setCancelable(true)
				.setIcon(android.R.drawable.stat_notify_error)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case UserValues.VIEW_USER_REQ:
			if (resultCode == UserValues.SURVEY_RES) {
				setResult(UserValues.SURVEY_RES);
				finish();
			}
			break;
		}
	}
}
