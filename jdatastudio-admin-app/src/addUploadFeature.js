// in addUploadFeature.js
/**
 * Convert a `File` object returned by the upload input into a base 64 string.
 * That's not the most optimized way to store images in production, but it's
 * enough to illustrate the idea of data provider decoration.
 */
const convertFileToBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file.rawFile);

    reader.onload = () => resolve(reader.result);
    reader.onerror = reject;
});

/**
 * For posts update only, convert uploaded image in base 64 and attach it to
 * the `picture` sent property, with `src` and `title` attributes.
 */
const addUploadFeature = requestHandler => (type, resource, params) => {
    if (type === 'UPDATE' || type === 'CREATE') {
        // notice that following condition can be true only when `<ImageInput source="pictures" />` component has parameter `multiple={true}`
        // if parameter `multiple` is false, then data.pictures is not an array, but single object
        const newPictures = [];
        for (let key in params.data) {
            let p = params.data[key];
            if (p.rawFile && p.rawFile instanceof File) {
                p.name = key;
                newPictures.push(p);
            }
        }
        if (newPictures.length !== 0) {
            return Promise.all(newPictures.map(convertFileToBase64))
                .then(base64Pictures => base64Pictures.map((picture64, index) => ({
                    src: picture64,
                    title: `${newPictures[index].title}`,
                    name: `${newPictures[index].name}`,
                })))
                .then(transformedNewPictures => {
                    transformedNewPictures.map((transformedNewPicture, index) => {
                        params.data[transformedNewPicture.name] = transformedNewPicture;
                    });
                    requestHandler(type, resource, {
                        ...params,
                        data: {
                            ...params.data
                        },
                    })
                });
        }
        return requestHandler(type, resource, params);
    }
    // for other request types and resources, fall back to the default request handler
    return requestHandler(type, resource, params);
};

export default addUploadFeature;