import os
import shutil

# === Configuration ===
# Directory where this script is located (and where the source PNGs live)
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))

# Source directory: same as script directory
SOURCE_DIR = SCRIPT_DIR

# Destination directory: will be created as a subfolder named "emissive_output"
DEST_DIR = SOURCE_DIR

# List of keywords to match in file names (case-insensitive)
KEYWORDS = [ ]

# Suffix to append before the file extension
SUFFIX = "_e"

# === Prepare destination folder ===
os.makedirs(DEST_DIR, exist_ok=True)

# === Process files ===
for filename in os.listdir(SOURCE_DIR):
    # Only consider PNG files
    if not filename.lower().endswith(".png"):
        continue

    # Check if any keyword appears in the filename
    lowercase_name = filename.lower()
    if any(keyword.lower() in lowercase_name for keyword in KEYWORDS):
        src_path = os.path.join(SOURCE_DIR, filename)
        name, ext = os.path.splitext(filename)
        new_name = f"{name}{SUFFIX}{ext}"
        dest_path = os.path.join(DEST_DIR, new_name)

        shutil.copy2(src_path, dest_path)
        print(f"Copied: {filename} → {new_name}")

print("Done!")
