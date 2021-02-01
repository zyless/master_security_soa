import pandas as pd
import glob
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import seaborn as sns

pd.set_option('display.max_columns', None)
# reading all the csv files and transforming them into one dataframe
df = pd.concat(map(pd.read_csv, glob.glob("./sa/*.csv")))
# deleting all columns beside critical and deaths
df = df[['critical', 'deaths']]

# crating the kmeans object from sklearn
kmeans = KMeans(n_clusters=2, n_init=100)
# starting the kmeans analyzing
kmeans.fit(df)
P = kmeans.predict(df)

# plotting the results with seaborn
sns_plot = sns.scatterplot(x=df.critical, y=df.deaths, hue=P, legend=False)
sns_plot.figure.savefig('./sa/sa.png')
