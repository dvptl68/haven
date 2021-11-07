package com.example.hackathon2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon2021.R
import com.example.hackathon2021.adapters.HotlinesAdapter

class HotlinesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_hotlines, container, false)

        // Hotline data.
        val hotlines = listOf(
            listOf("911", "In case of emergency, dial 911",
                "911"),
            listOf("Call 211", "Get help paying bills, finding food, and locating other resources" +
                    "near you. Call 211 now for confidential help from a caring expert.",
                "211"),
            listOf("National Suicide Prevention Lifeline", "We can all help prevent suicide. The " +
                    "Lifeline provides 24/7, free and confidential support for people in distress, "
                    + "prevention and crisis resources for you or your loved ones, and best " +
                    "practices for professionals in the United States.",
                "(800) 273-8255"),
            listOf("Crisis Text Line", "Crisis Text Line is here for any crisis. A live, trained " +
                    "Crisis Counselor receives the text and responds, all from our secure online " +
                    "platform. The volunteer Crisis Counselor will help you move from a hot moment"
                    + " to a cool moment.",
                "Text HOME to 741741"),
            listOf("National Domestic Violence Hotline", "Contacts to The Hotline can expect " +
                    "highly-trained, expert advocates to offer free, confidential, and " +
                    "compassionate support, crisis intervention information, education, and " +
                    "referral services in over 200 languages.",
                "(800) 799-7233"),
            listOf("Substance Abuse and Mental Health Services Administration National Helpline",
                "SAMHSA’s National Helpline, is a confidential, free, 24-hour-a-day, " +
                        "365-day-a-year, information service, in English and Spanish, for " +
                        "individuals and family members facing mental and/or substance use " +
                        "disorders. This service provides referrals to local treatment " +
                        "facilities, support groups, and community-based organizations. Callers " +
                        "can also order free publications and other information.",
                "(800) 662-4357"),
            listOf("CDC National HIV and AIDS Hotline",
                "A service of the United States Department of Health and Human Services (HHS), " +
                        "offers access to the latest, federally approved HIV/AIDS medical " +
                        "practice guidelines, HIV treatment and prevention clinical trials, and " +
                        "other research information for health care providers, researchers, " +
                        "people affected by HIV/AIDS, and the general public.",
                "(800) 232-4636")
        )

        // Apply the HotlinesAdapter to the RecyclerView.
        val resultAdapter = HotlinesAdapter(view.context, hotlines)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = resultAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}