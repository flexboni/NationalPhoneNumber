package com.boni.nationalphonenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.ShortNumberInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val phoneUtil = PhoneNumberUtil.getInstance()
    val shortInfo = ShortNumberInfo.getInstance()
    val defaultCountry = "KR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateButton.setOnClickListener {
            val phoneNumber = numberTextField.text.toString()
            var country = countryTextField.text.toString()

            if (TextUtils.isEmpty(country)) {
                 country = defaultCountry
            }

            when {
                TextUtils.isEmpty(phoneNumber) -> {
                    Toast.makeText(applicationContext, "번호 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    try {
                        val number = phoneUtil.parseAndKeepRawInput(phoneNumber, country)

                        if (!phoneUtil.isValidNumber(number)) {
                            Toast.makeText(
                                applicationContext,
                                "번호가 유효하지 않습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val prettyFormat =
                                phoneUtil.formatInOriginalFormat(number, defaultCountry)
                            val internationalFormat = phoneUtil.format(
                                number,
                                PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
                            )

                            resultText.text =
                                "pretty : $prettyFormat \ninternational : $internationalFormat"
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            applicationContext,
                            "번호가 유효하지 않습니다. \n $e.message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }
            }
        }
    }
}