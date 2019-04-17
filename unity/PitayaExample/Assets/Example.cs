using System.Collections.Generic;
using System.IO;
using UnityEngine;
using Pitaya;

public class Example : MonoBehaviour
{
	private PitayaClient _client;
	private bool _connected;
	private bool _requestSent;

	// Use this for initialization
	private void Start()
	{
		// _client = new PitayaClient("ca.crt");
		_client = new PitayaClient();
		_connected = false;
		_requestSent = false;

		_client.NetWorkStateChangedEvent += (ev) =>
		{
			if (ev == PitayaNetWorkState.Connected)
			{
				Debug.Log("Successfully connected!");
				_connected = true;
			}
			else if (ev == PitayaNetWorkState.FailToConnect)
			{
				Debug.Log("Failed to connect");
			}
		};

		_client.Connect("a1d127034f31611e8858512b1bea90da-838011280.us-east-1.elb.amazonaws.com", 3251,
			new Dictionary<string, string>
            {
                {"oi", "mano"}
            });
	}

	// Update is called once per frame
	private void Update()
	{
		if (_connected && !_requestSent)
		{
			_client.Request("connector.getsessiondata",
				(data) =>
                {
					Debug.Log("Got request data: " + data);
	                File.WriteAllText("/Users/lhahn/Downloads/OH_MY_GOD.txt", "I Got the request data: " + data);
                },
				(err) =>
				{
					Debug.LogError("Got error: code = " + err.Code + ", msg = " + err.Msg);
				});
			_requestSent = true;
		}
	}

	private void OnDestroy()
	{
		if (_client != null)
		{
			_client.Dispose();
			_client = null;
		}
	}
}
