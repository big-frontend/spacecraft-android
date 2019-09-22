package com.hawksjamesf.spacecraft

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/22/2019  Sun
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that represents our items.
 */
@Entity
data class Cheese(@PrimaryKey(autoGenerate = true) val id: Int, val name: String)