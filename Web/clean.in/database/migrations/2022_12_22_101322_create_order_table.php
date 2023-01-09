<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('order', function (Blueprint $table) {
            $table->id();
            $table->string('id_user');
            $table->unsignedBigInteger('id_paket');
            $table->text('alamat');
            $table->text('image_url')->nullable();
            $table->smallInteger('berat');
            $table->tinyInteger('status');
            $table->integer('total_harga');
            $table->foreign('id_paket')->references('id')->on('paket')->onDelete('cascade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('order');
    }
};
