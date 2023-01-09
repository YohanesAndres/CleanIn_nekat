<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\API\order_controller;
use App\Http\Controllers\API\user_controller;



/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('order/index',[order_controller::class,'index']);
Route::get('order/show/{id}',[order_controller::class,'show']);
Route::post('order/store',[order_controller::class,'store']);
Route::post('order/update/{id}',[order_controller::class,'update']);
Route::get('order/destroy',[order_controller::class,'destroy']);
Route::get('order/showByStatus/{status}',[order_controller::class,'showByStatus']);
Route::get('order/getOrderByIdUser/{id_user}',[order_controller::class,'getOrderByIdUser']);
Route::get('order/showBypaket/{paket}',[order_controller::class,'showBypaket']);

Route::get('user/index',[user_controller::class,'index']);
Route::get('user/show/{id}',[user_controller::class,'show']);
Route::post('user/store',[user_controller::class,'store']);
Route::post('user/update/{id}',[user_controller::class,'update']);
Route::get('user/destroy',[user_controller::class,'destroy']);
