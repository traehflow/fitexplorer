DROP VIEW IF EXISTS public.heatmap;
CREATE VIEW public.heatmap AS
SELECT floor(f.latitude * 1000::double precision) / 1000::double precision AS latitude,
       floor(f.longitude * 1000::double precision) / 1000::double precision AS longitude,
       count(*) AS count
FROM fitunit f
WHERE f.latitude IS NOT NULL AND f.longitude IS NOT NULL
GROUP BY (floor(f.latitude * 1000::double precision)), (floor(f.longitude * 1000::double precision));
