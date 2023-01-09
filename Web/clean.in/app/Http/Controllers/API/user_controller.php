<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\User;

class user_controller extends Controller
{

    public function index()
    {
        $user = User::all();

        if($user) {
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $user
            ]);

            } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ],400);
            }

    }

    public function store(Request $request)
    {

        try {
            //code
            $user = new User;
            $user->user_id = $request->user_id;
            $user->name_user = $request->name_user;
            $user->email = $request->email;
            $user->alamat = $request->alamat;
            $user->no_telp = $request->no_telp;
            $user->save();
    
            return response()->json([
                'status' => 200, //berhasil
                'message' => 'berhasil',
                'data'=> $user
            ]);

            } catch (\Exception $e) {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ],400);
            }
    }

    public function show($user_id)
    {
        $user = User::where('id_user', '=',$user_id)->get();
        
        if($user) {
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $user
            ]);

            } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ],400);
            }
        

    }

    public function update(Request $request, $id)
    {
        
        try {
            //code
            $user = User::find($id);
            if ($user){
                if ($request->has('name_user')) {
                    $user->name_user = $request->name_user;

                }
                if ($request->has('alamat')) {
                    $user->alamat = $request->alamat;
                }
                if ($request->has('no_telp')) {
                    $user->no_telp = $request->no_telp;
                }
                $user->save();
                return response()->json([
                    'status' => 200, //berhasil
                    'message' => 'berhasil',
                    'data'=> $user
                ]);
            } else {
                return response()->json([
                    'status' => 400, 
                    'message' =>'gagal',
                    ],400);
            }



            } catch (\Exception $e) {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ],400);
            }    
    }

    public function destroy($id)
    {
        $user = User::find($id);

        if($user) {
            $user->delete();
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $user
            ]);

            } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ],400);
            }

    }

}
