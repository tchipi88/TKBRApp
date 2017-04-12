(function() {
    'use strict';

    angular
        .module('app')
        .controller('VenteDialogController', VenteDialogController);

    VenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Vente','Client'];

    function VenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Vente ,Client) {
        var vm = this;

        vm.vente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.clients = Client.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vente.id !== null) {
                Vente.update(vm.vente, onSaveSuccess, onSaveError);
            } else {
                Vente.save(vm.vente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:venteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateEmission = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
