/* Copyright 2019 Levi Bard
 *
 * This file is a part of Tusky.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * Tusky is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Tusky; if not,
 * see <http://www.gnu.org/licenses>. */

package com.farahead.fediverse.adapter

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.farahead.fediverse.R
import com.farahead.fediverse.db.AccountEntity
import com.farahead.fediverse.util.CustomEmojiHelper
import com.farahead.fediverse.util.loadAvatar

import kotlinx.android.synthetic.main.item_autocomplete_account.view.*

class AccountSelectionAdapter(context: Context) : ArrayAdapter<AccountEntity>(context, R.layout.item_autocomplete_account) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.item_autocomplete_account, parent, false)
        }
        view!!

        val account = getItem(position)
        if (account != null) {
            val username = view.username
            val displayName = view.display_name
            val avatar = view.avatar
            username.text = account.fullName
            displayName.text = CustomEmojiHelper.emojifyString(account.displayName, account.emojis, displayName)

            val avatarRadius = avatar.context.resources.getDimensionPixelSize(R.dimen.avatar_radius_42dp)
            val animateAvatar = PreferenceManager.getDefaultSharedPreferences(avatar.context)
                    .getBoolean("animateGifAvatars", false)

            loadAvatar(account.profilePictureUrl, avatar, avatarRadius, animateAvatar)

        }

        return view
    }
}