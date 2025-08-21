from flask import Flask, jsonify, request
import pickle
import numpy as np
import os

app = Flask(__name__)
script_dir = os.path.dirname(os.path.realpath(__file__))
model_path = os.path.join(script_dir, "student_placement.pkl")

# Load the model
try:
    model = pickle.load(open(model_path, 'rb'))
except Exception as e:
    raise RuntimeError(f"Error loading the model: {e}")

@app.route("/")
def home():
    return "Selamat Datang di Workshop"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()  
        df = data['Database Fundamentals']
        ca = data['Computer Architecture']
        dcs = data['Distributed Computing Systems']
        csecurity = data['Cyber Security']
        n = data['Networking']
        sd = data['Software Development']
        ps = data['Programming Skills']
        pm = data['Project Management']
        cff = data['Computer Forensics Fundamentals']
        tm = data['Technical Communication']
        am = data['AI ML']
        se = data['Software Engineering']
        ba = data['Business Analysis']
        cs = data['Communication skills']
        ds = data['Data Science']
        ts = data['Troubleshooting skills']
        gd = data['Graphics Designing']

        input_query = np.array([[df, ca, dcs, csecurity, n, sd, ps, pm, cff, tm, am, se, ba, cs, ds, ts, gd]])
        
        result = model.predict(input_query)[0]  
        return jsonify({'Prediction': result})
    except KeyError as e:
        return jsonify({'error': f'Missing key: {e}'}), 400
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
    