<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use DB;

class paket_seeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('paket')->insert([
            'nama_paket' => 'Reguler',
            'harga' => 7000,
        ]);

        DB::table('paket')->insert([
            'nama_paket' => 'Take Home',
            'harga' => 10000,
        ]);

        DB::table('paket')->insert([
            'nama_paket' => 'Express',
            'harga' => 15000,
        ]);
    }
}
