<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\order;
use App\Models\paket;


class order_controller extends Controller
{
    public function showBypaket($paket) 
    {
        $order = order::where('id_paket', '=', $paket)->get();
        if($order) {
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $order
            ]);

        } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ]);
        }
    }

    public function showByStatus($status) 
    {
        $order = order::where('status', '=', $status)->get();
        if($order) {
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $order
            ]);

        } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ]);
        }
    }



    public function getOrderByIdUser($id_user) 
    {
        $order = order::where('id_user', '=', $id_user)->get();
        if($order) {
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $order
            ]);

        } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ]);
        }
    }


    public function index()
    {
        $order = order::all();
        if($order) {
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            'data' => $order
            ]);

            } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ]);
            }
    }

   
    public function store(Request $request)
    {
       

        try {
            //code
            $order = new order;
            $order->id_user = $request->id_user;
            $order->id_paket = $request->id_paket;
            $order->alamat = $request->alamat;
            $order->berat= $request->berat;
            $order->status = 1;
            $paket = paket::find($order->id_paket);
            $order->total_harga = $order->berat*$paket->harga;

            $order->save();
    
            return response()->json([
                'status' => 200, //berhasil
                'message' => 'berhasil',
                'data'=> $order
            ]);

            } catch (\Exception $e) {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            'error' => $e
            ],400);
            }
    }


    public function show($id)
    {
        $order = DB::select("Select u.user_id, order.alamat, order.berat, order.status, order.total_harga from order inner join users u on order.id_user = u.user_id where order.id = ?", [$id]);

        if($order) {
        return response()->json([
        'status' => 200,
        'message' => 'berhasil',
        'data' => $order
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
            $order = order::find($id);
            if ($order) {
                if ($request->has('status')) {
                    $order->status = $request->status;
                }
                if ($request->has('image_url')) {
                    $order->image_url = $request->image_url ? $request->image_url : "";
                }
                $order->save();

                return response()->json([
                    'status' => 200,
                    'message' => 'Order updated',
                    'data' => $order
                ]);
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
        $order = order::find($id);

        if($order) {
            $order->delete();
            return response()->json([
            'status' => 200,
            'message' => 'berhasil',
            ]);

            } else {
            return response()->json([
            'status' => 400, 
            'message' =>'gagal',
            ],400);
            }
    }
}
